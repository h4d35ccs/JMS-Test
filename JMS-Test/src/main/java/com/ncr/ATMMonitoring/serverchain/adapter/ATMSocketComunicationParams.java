package com.ncr.ATMMonitoring.serverchain.adapter;

import java.net.Socket;

import com.ncr.ATMMonitoring.socket.RequestThreadManager;

/**
 * @author Otto Abreu
 *
 */
public class ATMSocketComunicationParams {
    
    /** The Constant hashSeed. */
    private  String hashSeed;

    /** The Constant authOkMsg. */
    private  String authOkMsg;

    /** The Constant authErrorMsg. */
    private  String authErrorMsg ;

    /** The Constant authUpdateMsg. */
    private  String authUpdateMsg;

    /** The Constant endCommMsg. */
    private  String endCommMsg;
    
    /** The agent port. */
    private int agentPort;

    /** The response time out. */
    private int timeOut;

    /** The parent manager. */
    private RequestThreadManager parentRequestThreadManager;

    /** The number of ips to notify. */
    private int requestNumber;
    
    private Socket comunicationSocket;
    
    
    public String getHashSeed() {
        return hashSeed;
    }

    public void setHashSeed(String hashSeed) {
        this.hashSeed = hashSeed;
    }

    public String getAuthOkMsg() {
        return authOkMsg;
    }

    public void setAuthOkMsg(String authOkMsg) {
        this.authOkMsg = authOkMsg;
    }

    public String getAuthErrorMsg() {
        return authErrorMsg;
    }

    public void setAuthErrorMsg(String authErrorMsg) {
        this.authErrorMsg = authErrorMsg;
    }

    public String getAuthUpdateMsg() {
        return authUpdateMsg;
    }

    public void setAuthUpdateMsg(String authUpdateMsg) {
        this.authUpdateMsg = authUpdateMsg;
    }

    public String getEndCommMsg() {
        return endCommMsg;
    }

    public void setEndCommMsg(String endCommMsg) {
        this.endCommMsg = endCommMsg;
    }

    public int getAgentPort() {
        return agentPort;
    }

    public void setAgentPort(int agentPort) {
        this.agentPort = agentPort;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public RequestThreadManager getParentRequestThreadManager() {
        return parentRequestThreadManager;
    }

    public void setParentRequestThreadManager(
    	RequestThreadManager parentRequestThreadManager) {
        this.parentRequestThreadManager = parentRequestThreadManager;
    }

    public int getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    public Socket getComunicationSocket() {
        return comunicationSocket;
    }

    public void setComunicationSocket(Socket comunicationSocket) {
        this.comunicationSocket = comunicationSocket;
    }



}
