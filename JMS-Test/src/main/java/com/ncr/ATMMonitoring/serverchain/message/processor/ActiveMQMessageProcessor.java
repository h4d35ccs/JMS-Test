package com.ncr.ATMMonitoring.serverchain.message.processor;

import javax.jms.Message;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.DataStructure;

/**
 * @author Otto Abreu
 * 
 */
public abstract class ActiveMQMessageProcessor extends MessageProcessor {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ncr.ATMMonitoring.serverchain.message.processor.MessageProcessor#
     * processReceivedMessage(javax.jms.Message)
     */
    @Override
    public void processReceivedMessage(Message message) {

	ActiveMQMessage msg = this
		.extractActiveMQMessageFromJMSMessage(message);
	DataStructure ds = msg.getDataStructure();
	this.processByCommandType(ds);

    }

    protected ActiveMQMessage extractActiveMQMessageFromJMSMessage(
	    Message message) {
	ActiveMQMessage msg = null;

	if (message instanceof ActiveMQMessage) {

	    msg = (ActiveMQMessage) message;

	} else {

	    this.generateArgumentExeption(
		    "The message received should be instance of  ActiveMQMessage, received: ",
		    message.getClass());
	}

	return msg;
    }

   

    private void processByCommandType(DataStructure ds) {
	if (ds != null && (ds.getDataStructureType() == this.getCommandType())) {

	    this.processMessage(ds);
	}
    }
    protected abstract byte getCommandType();
    
    protected abstract void processMessage(DataStructure ds);

    

}
