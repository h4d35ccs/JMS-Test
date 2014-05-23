package com.ncr.ATMMonitoring.serverchain.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import com.ncr.ATMMonitoring.serverchain.adapter.exception.ATMUpdateRequestException;
import com.ncr.ATMMonitoring.serverchain.message.specific.outgoing.UpdateDataRequest;
import com.ncr.ATMMonitoring.socket.RequestThreadManager;
import com.ncr.ATMMonitoring.updatequeue.ATMUpdateInfo;
import com.ncr.ATMMonitoring.utils.Utils;
import com.ncr.serverchain.MessagePublisher;
import com.ncr.serverchain.NodeInformation;
import com.ncr.serverchain.NodePosition;
import com.ncr.serverchain.ServerchainMainBeanFactory;
import com.ncr.serverchain.message.wrapper.MessageWrapper;

/**
 * @author Otto Abreu
 * 
 */
public class ATMUpdateRequestAdapterImpl implements ATMUpdateRequestAdapter {

    private static Logger logger = Logger
	    .getLogger(ATMUpdateRequestAdapterImpl.class);

    private ATMUpdateInfo updateInfo;

    private ATMSocketComunicationParams socketComunicationParams;

    private NodeInformation nodeInformation;

    private MessagePublisher messagePublisher;

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.adapter.ATMUpdateRequestAdapter#
     * setupAdapter(com.ncr.ATMMonitoring.updatequeue.ATMUpdateInfo,
     * com.ncr.ATMMonitoring.serverchain.adapter.ATMSocketComunicationParams)
     */
    @Override
    public void setupAdapter(ATMUpdateInfo updateInfo,
	    ATMSocketComunicationParams socketComunicationParams) {

	this.socketComunicationParams = socketComunicationParams;
	this.updateInfo = updateInfo;
	this.nodeInformation = this.getNodeInformation();
	this.messagePublisher = this.getMessagePublisher(); 

    }

    private NodeInformation getNodeInformation() {

	NodeInformation nodeInformation = ServerchainMainBeanFactory
		.getNodeInformationInstance();

	return nodeInformation;
    }

    private MessagePublisher getMessagePublisher() {
	MessagePublisher messagePublisher = ServerchainMainBeanFactory
		.getMessagePublisherInstance();
	return messagePublisher;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.adapter.ATMUpdateRequestAdapter#
     * requestATMUpdate()
     */
    @Override
    public void requestATMUpdate() {

	validateSetup();

	if (initateServerChain()) {
	    logger.debug("using serverchain");
	    publishUpdate();
	    
	} else if (initiateSocketConnection()) {
	    logger.debug("using socketConnection");
	}
    }

    private void validateSetup() {
	if (this.socketComunicationParams == null && this.updateInfo == null) {
	    throw new ATMUpdateRequestException(ATMUpdateRequestException.SETUP_NOT_EXECUTED);
	}
    }

    private boolean initateServerChain() {

	boolean initiateServerChain = false;

	if (isRootAndIsNotOnlyNode()) {

	    initiateServerChain = true;
	}

	return initiateServerChain;
    }

    private void publishUpdate() {

	UpdateDataRequest updateRequest = getUpdateMessage();

	this.messagePublisher.publishOutgoingMessage(updateRequest.getMatricula().intValue(),
		MessageWrapper.DEFAULT_OUTGOINGMESSAGE_INNER_MESSAGE
			+ this.nodeInformation.getLocalUrl(), updateRequest);

    }

    private UpdateDataRequest getUpdateMessage(){
	String requestIp = updateInfo.getAtmIp();
	long matricula = updateInfo.getAtmMatricula();

	UpdateDataRequest updateRequest = new UpdateDataRequest(requestIp,
		matricula);
	
	return updateRequest;
    }
    
    private boolean isRootAndIsNotOnlyNode() {

	NodePosition position = this.getNodePosition();

	if (position.equals(NodePosition.FIRST_NODE)) {

	    return true;

	} else {

	    return false;
	}
    }

    private boolean initiateSocketConnection() {

	boolean initiateSocketConnection = false;

	if (isLeafNode() || isOnlyNode()) {

	    initiateSocketConnection = true;
	}

	return initiateSocketConnection;
    }

    private boolean isLeafNode() {

	NodePosition position = this.getNodePosition();

	if (position.equals(NodePosition.LEAF_NODE)) {

	    return true;

	} else {

	    return false;
	}

    }

    private boolean isOnlyNode() {

	NodePosition position = this.getNodePosition();

	if (position.equals(NodePosition.ONLY_NODE)) {
	    logger.debug("is Only Node");
	    return true;

	} else {

	    return false;
	}

    }

    private NodePosition getNodePosition() {

	NodePosition position = this.nodeInformation.getNodePosition();

	return position;
    }

    /*-************************************************************************
     * code copied from RequestThread
     */
    /**
     * Code executed before requesting data from an ATM for confirming it is
     * reliable.
     * 
     * For confirming an ATM's reliability we send it a random string which it
     * must hash, along with a hardcoded one and a configurable one, and then
     * send us the result so we can check whether it matches the expected one.
     * If we detect the agent is using an old version of the configurable
     * string, then we send it the new version so it can replace it.
     * 
     * @param socket
     *            the socket
     * @return true, if successful
     */
    private boolean confirmIdentity(ATMSocketComunicationParams comParams) {

	Socket socket = comParams.getComunicationSocket();
	RequestThreadManager parent = comParams.getParentRequestThreadManager();
	String hashSeed = comParams.getHashSeed();
	String authOkMsg = comParams.getAuthOkMsg();
	String authUpdateMsg = comParams.getAuthUpdateMsg();
	String authErrorMsg = comParams.getAuthErrorMsg();

	try {
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    // Enviamos al agente la cadena base para el hash
	    String randomSeed = RandomStringUtils.randomAlphanumeric(20);
	    logger.info("Sending the authentication data [" + hashSeed
		    + "] to " + socket.getInetAddress().getHostAddress() + ":"
		    + socket.getPort());
	    out.println(randomSeed);

	    BufferedReader in = new BufferedReader(new InputStreamReader(
		    socket.getInputStream()));
	    // Recuperamos el hash
	    String response = in.readLine();
	    logger.info("Authentication data received [" + response
		    + "] from IP: " + socket.getInetAddress().getHostAddress());
	    String parentSeed = parent.getHashSeed();
	    String hash = Utils.getMD5Hex(Utils.getMD5Hex(parentSeed)
		    + Utils.getMD5Hex(hashSeed) + Utils.getMD5Hex(randomSeed));
	    if (response.equals(hash)) {
		// Confirmamos al agente que la autenticacion fue correcta
		out.println(authOkMsg);
		return true;
	    } else {
		String oldParentSeed = parent.getOldHashSeed();
		if ((oldParentSeed != null)
			&& (!oldParentSeed.equals(parentSeed))) {
		    hash = Utils.getMD5Hex(Utils.getMD5Hex(oldParentSeed)
			    + Utils.getMD5Hex(hashSeed)
			    + Utils.getMD5Hex(randomSeed));
		    if (response.equals(hash)) {
			logger.warn("Old hash seed has been detected in IP "
				+ socket.getInetAddress().getHostAddress()
				+ ". New one will be sent.");
			// Confirmamos al agente que la autenticaci�n fue
			// correcta y le pedimos que actualice su hash
			out.println(authUpdateMsg + ":" + oldParentSeed + ":"
				+ parentSeed);
			return true;
		    }
		}
	    }

	    // La autenticacion ha fallado, avisamos al agente y terminamos
	    out.println(authErrorMsg);
	} catch (SocketTimeoutException e) {
	    logger.error("We received no response during"
		    + " authentication from IP: "
		    + socket.getInetAddress().getHostAddress(), e);
	} catch (IOException e) {
	    logger.error("An exception was thrown while"
		    + " confirming identity of IP: "
		    + socket.getInetAddress().getHostAddress(), e);
	}
	return false;
    }

    /**
     * Request data json from an ATM.
     * 
     * @param ip
     *            the ATM ip
     * @throws Exception
     *             any kind of exception thrown with an error
     */
    private void requestDataJson(String ip,
	    ATMSocketComunicationParams comParams) throws Exception {

	RequestThreadManager parent = comParams.getParentRequestThreadManager();
	int agentPort = comParams.getAgentPort();
	String endCommMsg = comParams.getEndCommMsg();
	int timeOut = comParams.getTimeOut();

	try {
	    // Abrimos el socket y un buffer de lectura
	    Socket socket = RequestThreadManager.getClientSocketFactory()
		    .createSocket(ip, agentPort);

	    // Ponemos un timeout para la recepcion de datos
	    socket.setSoTimeout(timeOut * 1000);

	    // Confirmamos la identidad del agente
	    if (confirmIdentity(comParams)) {
		logger.info("Id confirmed for IP: " + ip);
	    } else {
		logger.error("Id couldn't be confirmed for IP: " + ip);
		socket.close();
		return;
	    }

	    BufferedReader in = new BufferedReader(new InputStreamReader(
		    socket.getInputStream()));
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    String json;
	    String endMsg = endCommMsg;
	    try {
		// Recuperamos el Json con los datos del DataStore
		json = in.readLine();
		logger.info("Data received from IP: " + ip);
		try {
		    Long matricula = parent.handleIpSuccess(json);
		    if (matricula != null) {
			// Tenemos una matrícula nueva, así que la enviamos al
			// agente
			logger.info("New generated id " + matricula
				+ " will be sent to IP: " + ip);
			endMsg += ":" + matricula;
		    }
		} catch (Exception e) {
		    logger.error(
			    "An error happened while saving data received from ip: "
				    + ip + "\nJson: " + json, e);
		}
		// Enviamos el mensaje que confirma el final de la comunicacion
		logger.info("Sending final comm message to IP: " + ip);
		out.println(endMsg);
	    } catch (SocketTimeoutException e) {
		logger.error(ATMUpdateRequestException.NO_RESPONSE_ERROR + ip,
			e);
		throw new ATMUpdateRequestException(
			ATMUpdateRequestException.NO_RESPONSE_ERROR + ip, e);
	    } finally {
		// Cerramos los recursos abiertos
		socket.close();
	    }
	    // Todo ha ido bien, terminamos.
	    return;

	} catch (UnknownHostException e) {
	    logger.error(ATMUpdateRequestException.UNKNOWN_HOST_ERROR + ip, e);
	    throw new ATMUpdateRequestException(
		    ATMUpdateRequestException.UNKNOWN_HOST_ERROR + ip, e);
	} catch (IOException e) {
	    logger.error(
		    ATMUpdateRequestException.GENERAL_ERROR_WHILE_REQUESTING_DATA
			    + ip, e);
	    throw new ATMUpdateRequestException(
		    ATMUpdateRequestException.GENERAL_ERROR_WHILE_REQUESTING_DATA
			    + ip, e);
	}
    }

}
