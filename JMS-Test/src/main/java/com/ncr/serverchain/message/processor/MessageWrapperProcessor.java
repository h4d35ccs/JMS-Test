/**
 * 
 */
package com.ncr.serverchain.message.processor;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.serverchain.NodeInformation;
import com.ncr.serverchain.message.wrapper.MessageWrapper;

/**
 * <pre>
 * Class that apply logic to an ObjectMessage.
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
@Component
public abstract class MessageWrapperProcessor extends MessageProcessor {

    @Autowired
    private NodeInformation nodeInformation;

    private static final Logger logger = Logger
	    .getLogger(MessageWrapperProcessor.class);

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

	    logger.error(
		    "Can not extract the MessageWrapper from the JMS Message due an exception ",
		    e);
	}

	return wrapper;
    }

    private ObjectMessage getObejctMessageFromJMSMessage(Message message) {

	ObjectMessage objectMessage = null;

	this.validateThatMessageIsAnObjectMessage(message);

	objectMessage = ((ObjectMessage) message);

	return objectMessage;
    }

    private void validateThatMessageIsAnObjectMessage(Message message) {
	if (!(message instanceof ObjectMessage)) {
	    this.generateArgumentExeption(
		    "the message class should be ObjectMessage, received: ",

		    message.getClass());
	}
    }

    private MessageWrapper getMessageWrapperFromJMSMessage(
	    ObjectMessage objectMessage) throws JMSException {

	Serializable extractedFromJMSMessage = objectMessage.getObject();

	validateExtractedMessageIsAnInstanceOfMessageWrapper(extractedFromJMSMessage);

	MessageWrapper wrapper = (MessageWrapper) extractedFromJMSMessage;

	return wrapper;
    }

    private void validateExtractedMessageIsAnInstanceOfMessageWrapper(
	    Serializable extractedFromJMSMessage) {

	if (!(extractedFromJMSMessage instanceof MessageWrapper)) {

	    this.generateArgumentExeption(
		    "the message inside the ObjectMessage  should be instance of  MessageWrapper, received: "

		    , extractedFromJMSMessage);
	}
    }

    protected abstract void processMessage(MessageWrapper wrapper);

    protected String getLocalBrokerURL() {
	return this.nodeInformation.getLocalBrokerUrl();
    }

}
