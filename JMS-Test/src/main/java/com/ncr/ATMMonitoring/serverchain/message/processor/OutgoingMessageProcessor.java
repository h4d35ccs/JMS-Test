package com.ncr.ATMMonitoring.serverchain.message.processor;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.OutgoingMessage;
import com.ncr.ATMMonitoring.serverchain.topicactors.producer.OutgoingMessageProducer;

/**
 * @author Otto Abreu
 * 
 */
@Component("outgoingMessageProcessor")
public class OutgoingMessageProcessor implements MessageProcessor {

    @Autowired
    private OutgoingMessageProducer outgoingProducer;

    private static final Logger logger = Logger
	    .getLogger(OutgoingMessageProcessor.class);

    @Override
    public void processReceivedMessage(Message message) {

	OutgoingMessage outgoingMessage = this.getOutgoingMessage(message);
	processOutgoingMessage(outgoingMessage);
    }

    private void processOutgoingMessage(Serializable message) {

	logger.debug("passing message:" + message);

	this.outgoingProducer.sendMessage(message);
    }

    private OutgoingMessage getOutgoingMessage(Message message) {
	
	OutgoingMessage outgoingMessage = null;

	try {
	   
	    ObjectMessage objectMessage = ((ObjectMessage) message);
	    outgoingMessage = (OutgoingMessage) objectMessage.getObject();
	    
	} catch (JMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return outgoingMessage;
    }
}
