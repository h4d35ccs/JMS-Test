package com.ncr.serverchain.message.specific;

/**
 * Represent a direct message sent to a specific node
 * @author Otto Abreu
 *
 */
public abstract class DirectCommunicationMessage implements SpecificMessage {


    private static final long serialVersionUID = 1L;
    
    private String comunicateToNode;
    

    public DirectCommunicationMessage(String comunicateToNode) {
	super();
	this.comunicateToNode = comunicateToNode;
    }

    public String getComunicateToNode() {
        return comunicateToNode;
    }


}
