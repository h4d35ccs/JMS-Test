package com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ncr.ATMMonitoring.routertable.RouterTableHandler;
import com.ncr.ATMMonitoring.serverchain.message.specific.incoming.NetworkMappingResponse;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.BroadcastType;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.IncomingMessage;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageWrapper;

/**
 * <pre>
 * Strategy that process NetworkMappingRequest messages.
 * 
 * This strategy travels all the nodes until it reaches the leaf, there it creates 
 * a response message ( NetworkMappingResponse)  and puts the information of the leaf node inside.
 * After that the message is returned to the parent ( BroadcastType.TURN_BACK)
 * 
 * @author Otto Abreu
 * <pre>
 */
public class NetworkMappingRequestStrategy extends BaseStrategy {

    private NetworkMappingResponse response;
    private static final Logger logger = Logger
	    .getLogger(NetworkMappingRequestStrategy.class);
    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy#canProcessSpecificMessage()
     */
    @Override
    public boolean canProcessSpecificMessage() {

	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy#processSpecificMessage()
     */
    @Override
    public void processSpecificMessage() {

	if (this.isLeaf()) {
	    this.generateResponseInLeaf();
	    logger.debug("NetworkMap Response generated");
	}

    }

  

    private void generateResponseInLeaf() {
	 
	this.response = new NetworkMappingResponse();
	 String nodeUrlAndPort =  this.nodeInformation.getLocalUrl();
	 String parentUrl = this.nodeInformation.getParentUrl();
	 String routerTable = null;
	 routerTable = RouterTableHandler.tableTotring();
	 this.response.addNodeInformationLeaforMiddle(nodeUrlAndPort, routerTable, parentUrl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy#broadcastDirection()
     */
    @Override
    public BroadcastType broadcastDirection() {
	BroadcastType broadcast = BroadcastType.ONE_WAY;
	
	if(this.isLeaf()){
	    
	    broadcast = BroadcastType.TURN_BACK;
	}
	
	return broadcast;
    }
    
    @Override
    public MessageWrapper getTurnBackMessage() {
	
	MessageWrapper turnbackMessage =  new IncomingMessage("Network mapping response",(int)new Date().getTime());;
	turnbackMessage.setSpecificMessage(this.response);
	return turnbackMessage;
    }

}
