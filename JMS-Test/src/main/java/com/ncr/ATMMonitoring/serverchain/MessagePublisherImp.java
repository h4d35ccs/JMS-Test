package com.ncr.ATMMonitoring.serverchain;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageWrapper;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.OutgoingMessage;
import com.ncr.ATMMonitoring.serverchain.topicactor.producer.GenericMessageProducer;

/**
 * Concrete class of MessagePublisher
 * 
 * @author Otto Abreu
 * 
 */
@Component
public class MessagePublisherImp implements MessagePublisher {

    @Resource(name = "outgoingMessageProducer")
    private GenericMessageProducer outgoingProducer;

    @Resource(name = "incomingMessageProducer")
    private GenericMessageProducer incomingProducer;

    private static final String DEFAULT_WRAPPER_MESSAGE = "";

    private static int DEFAUTL_ID = -1;

    @Override
    public void publishOutgoingMessage(MessageWrapper messageWrapper) {
	this.outgoingProducer.sendMessage(messageWrapper);
    }

    @Override
    public void publishOutgoingMessage(SpecificMessage message) {

	this.instanciateOutgoingMessage(DEFAUTL_ID, DEFAULT_WRAPPER_MESSAGE,
		message);
    }

    @Override
    public void publishOutgoingMessage(int id, SpecificMessage wrappedMessage) {

	this.instanciateOutgoingMessage(id, DEFAULT_WRAPPER_MESSAGE,
		wrappedMessage);
    }

    @Override
    public void publishOutgoingMessage(String wraperMessage,
	    SpecificMessage wrappedMessage) {

	this.instanciateOutgoingMessage(DEFAUTL_ID, wraperMessage,
		wrappedMessage);
    }

    @Override
    public void publishOutgoingMessage(int id, String wrapperMessage,
	    SpecificMessage wrappedMessage) {

	MessageWrapper wrapper = this.instanciateOutgoingMessage(id,
		wrapperMessage, wrappedMessage);
	this.outgoingProducer.sendMessage(wrapper);
    }

    @Override
    public void publishIncomingMessage(MessageWrapper messageWrapper) {
	this.incomingProducer.sendMessage(messageWrapper);

    }

    @Override
    public void publishIncomingMessage(SpecificMessage wrappedMessage) {
	this.instanciateIncomingMessage(DEFAUTL_ID, DEFAULT_WRAPPER_MESSAGE,
		wrappedMessage);
    }

    @Override
    public void publishIncomingMessage(int id, SpecificMessage wrappedMessage) {
	this.instanciateIncomingMessage(id, DEFAULT_WRAPPER_MESSAGE,
		wrappedMessage);
    }

    @Override
    public void publishIncomingMessage(String wraperMessage,
	    SpecificMessage wrappedMessage) {
	this.instanciateIncomingMessage(DEFAUTL_ID, wraperMessage,
		wrappedMessage);
    }

    @Override
    public void publishIncomingMessage(int id, String wrapperMessage,
	    SpecificMessage message) {

	MessageWrapper wrapper = this.instanciateIncomingMessage(id,
		wrapperMessage, message);
	this.incomingProducer.sendMessage(wrapper);
    }

    private MessageWrapper instanciateOutgoingMessage(int id,
	    String wrapperMessage, SpecificMessage specificMessage) {

	MessageWrapper outgoingMessage = new OutgoingMessage(wrapperMessage, id);
	outgoingMessage.setSpecificMessage(specificMessage);
	return outgoingMessage;
    }

    private MessageWrapper instanciateIncomingMessage(int id,
	    String wrapperMessage, SpecificMessage specificMessage) {

	MessageWrapper outgoingMessage = new OutgoingMessage(wrapperMessage, id);
	outgoingMessage.setSpecificMessage(specificMessage);
	return outgoingMessage;
    }
}
