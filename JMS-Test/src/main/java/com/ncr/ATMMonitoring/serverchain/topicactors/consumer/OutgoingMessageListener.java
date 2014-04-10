package com.ncr.ATMMonitoring.serverchain.topicactors.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.MessageProcessor;
import com.ncr.ATMMonitoring.serverchain.message.OutgoingMessage;

@Component
public class OutgoingMessageListener implements MessageListener {

    private static final Logger logger = Logger
	    .getLogger(OutgoingMessageListener.class);

    @Autowired
    private MessageProcessor messageProcessor;

    @Override
    public void onMessage(Message outgoingMessage) {
	try {

	    if (outgoingMessage instanceof ObjectMessage) {

		ObjectMessage objectOutgoingMessage = (ObjectMessage) outgoingMessage;

		this.processReceivedMessage(objectOutgoingMessage);

	    }

	} catch (JMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    private void processReceivedMessage(ObjectMessage objectOutgoingMessage)
	    throws JMSException {

	OutgoingMessage sentMessage = (OutgoingMessage) objectOutgoingMessage
		.getObject();

	logger.debug("Received Message in consumer" + sentMessage);

	this.messageProcessor.processReceivedMessage(sentMessage);
    }
}
