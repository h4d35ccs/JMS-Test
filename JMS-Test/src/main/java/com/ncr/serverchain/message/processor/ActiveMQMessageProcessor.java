package com.ncr.serverchain.message.processor;

import javax.jms.Message;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.DataStructure;

/**
 * <pre>
 * Class that apply logic to an ActiveMQMessage.
 * 
 * The concrete classes have to implement the method processMessage in order to execute the logic, 
 * this means that this class implements a Template method pattern,
 * where the logic related to the message processing is delegated to the concrete classes
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Template_method_pattern">Template method pattern</a>
 * 
 * @author Otto Abreu
 * 
 * </pre>
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
	
	this.validateThatMessageIsAnActiveMQMessage(message);
	ActiveMQMessage  msg = (ActiveMQMessage) message;

	return msg;
    }
    
    private void validateThatMessageIsAnActiveMQMessage( Message message){
	if (!(message instanceof ActiveMQMessage)) {
	    this.generateArgumentExeption(
		    "The message received should be instance of  ActiveMQMessage, received: ",
		    message.getClass());
	}
    }

   

    private void processByCommandType(DataStructure ds) {
	if (ds != null && (ds.getDataStructureType() == this.getCommandType())) {

	    this.processMessage(ds);
	}
    }
    
    
    protected abstract byte getCommandType();
    
    protected abstract void processMessage(DataStructure ds);

    

}
