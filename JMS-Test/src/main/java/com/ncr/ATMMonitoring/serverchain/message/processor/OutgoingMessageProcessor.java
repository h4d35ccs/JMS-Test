package com.ncr.ATMMonitoring.serverchain.message.processor;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.ChainLinkInformation;
import com.ncr.ATMMonitoring.serverchain.message.Stamper;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageStamp;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageWrapper;
import com.ncr.ATMMonitoring.serverchain.topicactor.producer.OutgoingMessageProducer;

/**
 * @author Otto Abreu
 * 
 */
@Component("outgoingMessageProcessor")
public class OutgoingMessageProcessor extends ObjectMessageProcessor implements Stamper {

    @Autowired
    private OutgoingMessageProducer outgoingProducer;

    @Autowired
    private ChainLinkInformation chainLinkInformation;

    private static final Logger logger = Logger
	    .getLogger(OutgoingMessageProcessor.class);

    @Override
    protected void processMessage(MessageWrapper message) {
	
	this.setMessageStamp(message);
	logger.debug("received message in outgoing processor:" + message);
	
	if (chainLinkInformation.isMiddleNode()) {

	    logger.debug("passing message:" + message);
	    this.outgoingProducer.sendMessage(message);

	} else if (this.chainLinkInformation.isLeaf()) {

	}
    }

    @Override
    public void setMessageStamp(Serializable message) {
	MessageStamp messageStamp = new MessageStamp();
	messageStamp.setReceivedStampToMessageWraper(message, this.chainLinkInformation.getLocalBrokerUrl());
	
    }

}
