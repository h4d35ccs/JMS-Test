package com.ncr.ATMMonitoring.serverchain.message;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.topicactors.producer.OutgoingMessageProducer;

/**
 * @author Otto Abreu
 * 
 */
@Component
public class MessageProcessor {

    @Autowired
    private OutgoingMessageProducer outgoingProducer;

    private static final Logger logger = Logger
	    .getLogger(MessageProcessor.class);

    public void processReceivedMessage(Serializable message) {

	if (message instanceof OutgoingMessage) {

	    processOutgoingMessage(message);
	}
    }

    private void processOutgoingMessage(Serializable message) {

	logger.debug("passing message:" + message);

	this.outgoingProducer.sendMessage(message);
    }
}
