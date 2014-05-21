package com.ncr.serverchain;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ncr.serverchain.message.specific.SpecificMessage;
import com.ncr.serverchain.message.wrapper.IncomingMessage;
import com.ncr.serverchain.message.wrapper.MessageWrapper;
import com.ncr.serverchain.message.wrapper.OutgoingMessage;
import com.ncr.serverchain.topicactor.producer.GenericMessageProducer;

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

    private String FORCE_USE_DEFAULT_WRAPPER_MESSAGE = "";

    private static int DEFAUTL_ID = (int)new Date().getTime();

    @Override
    public void publishOutgoingMessage(MessageWrapper messageWrapper) {
	this.outgoingProducer.sendMessage(messageWrapper);
    }

    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.MessagePublisher#publishOutgoingMessage(com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage)
     */
    @Override
    public void publishOutgoingMessage(SpecificMessage message) {

	MessageWrapper outmessage = this.instanciateOutgoingMessage(DEFAUTL_ID,
		FORCE_USE_DEFAULT_WRAPPER_MESSAGE, message);
	this.publishOutgoingMessage(outmessage);
    }
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.MessagePublisher#publishOutgoingMessage(int, com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage)
     */
    @Override
    public void publishOutgoingMessage(int id, SpecificMessage wrappedMessage) {

	MessageWrapper outmessage = this.instanciateOutgoingMessage(id, FORCE_USE_DEFAULT_WRAPPER_MESSAGE,
		wrappedMessage);
	this.publishOutgoingMessage(outmessage);
    }
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.MessagePublisher#publishOutgoingMessage(java.lang.String, com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage)
     */
    @Override
    public void publishOutgoingMessage(String wraperMessage,
	    SpecificMessage wrappedMessage) {

	MessageWrapper outmessage = this.instanciateOutgoingMessage(DEFAUTL_ID, wraperMessage,
		wrappedMessage);
	this.publishOutgoingMessage(outmessage);
    }
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.MessagePublisher#publishOutgoingMessage(int, java.lang.String, com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage)
     */
    @Override
    public void publishOutgoingMessage(int id, String wrapperMessage,
	    SpecificMessage wrappedMessage) {

	MessageWrapper wrapper = this.instanciateOutgoingMessage(id,
		wrapperMessage, wrappedMessage);
	
	this.publishOutgoingMessage(wrapper);
    }
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.MessagePublisher#publishIncomingMessage(com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageWrapper)
     */
    @Override
    public void publishIncomingMessage(MessageWrapper messageWrapper) {
	
	this.incomingProducer.sendMessage(messageWrapper);
    }

    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.MessagePublisher#publishIncomingMessage(com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage)
     */
    @Override
    public void publishIncomingMessage(SpecificMessage wrappedMessage) {
	MessageWrapper inMessage = this.instanciateIncomingMessage(DEFAUTL_ID,
		FORCE_USE_DEFAULT_WRAPPER_MESSAGE, wrappedMessage);
	this.publishIncomingMessage(inMessage);
    }
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.MessagePublisher#publishIncomingMessage(int, com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage)
     */
    @Override
    public void publishIncomingMessage(int id, SpecificMessage wrappedMessage) {
	
	MessageWrapper inMessage = this.instanciateIncomingMessage(id, FORCE_USE_DEFAULT_WRAPPER_MESSAGE,
		wrappedMessage);
	this.publishIncomingMessage(inMessage);
    }
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.MessagePublisher#publishIncomingMessage(java.lang.String, com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage)
     */
    @Override
    public void publishIncomingMessage(String wraperMessage,
	    SpecificMessage wrappedMessage) {
	
	MessageWrapper inMessage = this.instanciateIncomingMessage(DEFAUTL_ID, wraperMessage,
		wrappedMessage);
	this.publishIncomingMessage(inMessage);
    }
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.MessagePublisher#publishIncomingMessage(int, java.lang.String, com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage)
     */
    @Override
    public void publishIncomingMessage(int id, String wrapperMessage,
	    SpecificMessage message) {

	MessageWrapper inMessage = this.instanciateIncomingMessage(id,
		wrapperMessage, message);
	this.publishIncomingMessage(inMessage);
    }

    private MessageWrapper instanciateOutgoingMessage(int id,
	    String wrapperMessage, SpecificMessage specificMessage) {
	MessageWrapper outgoingMessage = null;

	if (wrapperMessage.equals(FORCE_USE_DEFAULT_WRAPPER_MESSAGE)) {

	    outgoingMessage = new OutgoingMessage(id);

	} else {
	    outgoingMessage = new OutgoingMessage(wrapperMessage, id);
	}

	outgoingMessage.setSpecificMessage(specificMessage);
	return outgoingMessage;
    }

    private MessageWrapper instanciateIncomingMessage(int id,
	    String wrapperMessage, SpecificMessage specificMessage) {

	MessageWrapper incomingMessage = null;
	
	if (wrapperMessage.equals(FORCE_USE_DEFAULT_WRAPPER_MESSAGE)) {
	    
	    incomingMessage = new IncomingMessage(id);
	    
	} else {
	    
	    incomingMessage = new IncomingMessage(wrapperMessage, id);
	}

	incomingMessage.setSpecificMessage(specificMessage);
	return incomingMessage;
    }
}
