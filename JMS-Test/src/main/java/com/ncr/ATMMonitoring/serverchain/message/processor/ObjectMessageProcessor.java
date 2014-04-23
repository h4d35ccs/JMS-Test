/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.message.processor;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import com.ncr.ATMMonitoring.serverchain.message.MessageWrapper;

/**
 * @author Otto Abreu
 * 
 */
public abstract class ObjectMessageProcessor extends MessageProcessor{

    @Override
    public void processReceivedMessage(Message message) {

	MessageWrapper outgoingMessage = this
		.extractObjectMessageFromJMSMessage(message);
	this.processMessage(outgoingMessage);
    }

    protected MessageWrapper extractObjectMessageFromJMSMessage(Message message) {
	MessageWrapper wrapper = null;

	try {

	    ObjectMessage objectMessage = this
		    .getObejctMessageFromJMSMessage(message);
	    wrapper = this.getMessageWrapperFromJMSMessage(objectMessage);

	} catch (JMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return wrapper;
    }

    private ObjectMessage getObejctMessageFromJMSMessage(Message message) {
	ObjectMessage objectMessage = null;

	if (message instanceof ObjectMessage) {

	    objectMessage = ((ObjectMessage) message);

	} else {
	    this.generateArgumentExeption(
		    "the message class should be ObjectMessage, received: ",

		    message.getClass());
	}

	return objectMessage;
    }

    private MessageWrapper getMessageWrapperFromJMSMessage(
	    ObjectMessage objectMessage) throws JMSException {

	MessageWrapper wrapper = null;

	Serializable extractedFromJMSMessage = objectMessage.getObject();

	if (extractedFromJMSMessage instanceof MessageWrapper) {
	    wrapper = (MessageWrapper) extractedFromJMSMessage;
	} else {

	    this.generateArgumentExeption(
		    "the message inside the ObjectMessage  should be instance of  MessageWrapper, received: "

		    , extractedFromJMSMessage);
	}
	return wrapper;
    }

    protected abstract void processMessage(MessageWrapper wrapper);

    
}
