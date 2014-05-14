package com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp;

import com.ncr.ATMMonitoring.serverchain.message.specific.DirectCommunicationMessage;

/**
 * <pre>
 * Abstract strategy class that holds basic logic to implement strategies 
 * related to send or request information to a specific node in the network
 * </pre>
 * @author Otto Abreu
 *
 */
public abstract class DirectNodeCommunication extends BaseStrategy {

    /* (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#canProcessSpecificMessage()
     */
    @Override
    public boolean canProcessSpecificMessage() {
	
	if (messageWasAddressedToThisNode()){
	   
	    return true;
	    
	}else{
	    
	    return true; 
	}
	
    }

    /**
     * Indicate if the message was addressed to the current node
     * @return boolean
     */
    protected boolean messageWasAddressedToThisNode(){
	
	String actualNode = this.nodeInformation.getLocalUrl();
	String nodeToComunicatewith = this.getDirectNodeComunication().getComunicateToNode();
	
	if(actualNode.equals(nodeToComunicatewith)){
	  
	    return true;
	    
	}else{
	    
	    return false;
	}
    }
    

    protected abstract DirectCommunicationMessage getDirectNodeComunication();

}
