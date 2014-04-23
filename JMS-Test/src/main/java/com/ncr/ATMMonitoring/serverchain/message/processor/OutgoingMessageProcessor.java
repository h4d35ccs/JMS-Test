package com.ncr.ATMMonitoring.serverchain.message.processor;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.apache.commons.lang.IllegalClassException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.ChainLinkInformation;
import com.ncr.ATMMonitoring.serverchain.message.OutgoingMessage;
import com.ncr.ATMMonitoring.serverchain.topicactor.producer.OutgoingMessageProducer;

/**
 * @author Otto Abreu
 * 
 */
@Component("outgoingMessageProcessor")
public class OutgoingMessageProcessor implements MessageProcessor {

    @Autowired
    private OutgoingMessageProducer outgoingProducer;
    
    @Autowired
    private ChainLinkInformation chainLinkInformation;

    private static final Logger logger = Logger
	    .getLogger(OutgoingMessageProcessor.class);

    @Override
    public void processReceivedMessage(Message message) {

	OutgoingMessage outgoingMessage = this.getOutgoingMessage(message);
	processOutgoingMessage(outgoingMessage);
    }

    private void processOutgoingMessage(Serializable message) {

	logger.debug("received message in outgoing processor:" + message);
	if(chainLinkInformation.isMiddleNode()){
	    logger.debug("passing message:" + message);
	    this.outgoingProducer.sendMessage(message);
	    
	}else if(this.chainLinkInformation.isLeaf()){
	    
	}
    }

    private OutgoingMessage getOutgoingMessage(Message message) {

	OutgoingMessage outgoingMessage = null;

	try {
	    if (message instanceof ObjectMessage) {
		ObjectMessage objectMessage = ((ObjectMessage) message);
		outgoingMessage = (OutgoingMessage) objectMessage.getObject();
	    } else {
		throw new IllegalArgumentException(
			"the message class should be ObjectMessage, received: "
				+ message.getClass());
	    }

	} catch (JMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return outgoingMessage;
    }
}
