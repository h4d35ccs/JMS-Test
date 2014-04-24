package com.ncr.ATMMonitoring.serverchain.message.processor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.ChainLinkInformation;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageWrapper;
import com.ncr.ATMMonitoring.serverchain.topicactor.producer.OutgoingMessageProducer;

/**
 * @author Otto Abreu
 * 
 */
@Component("outgoingMessageProcessor")
public class OutgoingMessageProcessor extends ObjectMessageProcessor {

    @Autowired
    private OutgoingMessageProducer outgoingProducer;
    
    @Autowired
    private ChainLinkInformation chainLinkInformation;

    private static final Logger logger = Logger
	    .getLogger(OutgoingMessageProcessor.class);

  
    
    @Override
    protected void processMessage(MessageWrapper message) {

	logger.debug("received message in outgoing processor:" + message);

	if(chainLinkInformation.isMiddleNode()){
	    
	    logger.debug("passing message:" + message);
	    this.outgoingProducer.sendMessage(message);
	    
	}else if(this.chainLinkInformation.isLeaf()){
	    
	}
    }

    
}
