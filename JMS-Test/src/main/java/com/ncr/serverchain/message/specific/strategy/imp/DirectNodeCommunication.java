package com.ncr.serverchain.message.specific.strategy.imp;

import org.apache.log4j.Logger;

import com.ncr.serverchain.message.specific.DirectCommunicationMessage;

/**
 * <pre>
 * Abstract strategy class that holds basic logic to implement strategies 
 * related to send or request information to a specific node in the network
 * </pre>
 * @author Otto Abreu
 *
 */
public abstract class DirectNodeCommunication extends BaseStrategy {

    
    private static final Logger logger = Logger
	    .getLogger(DirectNodeCommunication.class);

    /**
     * Indicate if the message was addressed to the current node
     * @return boolean
     */
    protected boolean messageWasAddressedToThisNode(){
	
	String actualNode = this.nodeInformation.getLocalUrl();
	String nodeToComunicatewith = this.getDirectNodeComunication().getComunicateToNode();
	logger.debug("Current node: "+actualNode+" destination node: "+nodeToComunicatewith);
	
	if(actualNode.equals(nodeToComunicatewith)){
	  
	    return true;
	    
	}else{
	    
	    return false;
	}
    }
    

    protected abstract DirectCommunicationMessage getDirectNodeComunication();

}
