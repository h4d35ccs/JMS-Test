package com.ncr.serverchain.executer;

import javax.annotation.Resource;
import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.serverchain.NodeInformation;
import com.ncr.serverchain.topicactor.consumer.TopicConsumer;
/**
 * class that checks the outgoing topic and consumes the messages that arrives
 * @author Otto Abreu
 *
 */
@Component("ConsumerExecuter")
public class OutgoingMessageConsumerExecuter {

    @Resource(name="outgoingMessageConsumer")
    private TopicConsumer consumer;
    
    @Autowired
    private NodeInformation nodePosition;

    private static final Logger logger = Logger
	    .getLogger(OutgoingMessageConsumerExecuter.class);

    @Scheduled(fixedDelay = 1000)
    public void runConsumer() {

	boolean hasParentAndShouldLaunchConsumer = this.nodePosition
		.hasParentNode();

	if (hasParentAndShouldLaunchConsumer) {
	    try {
		
		this.consumeMessage();
		 
	    } catch (JMSException e) {
		logger.error("error while consuming message: "+e.getMessage(),e);
	    }

	}

    }
    
    private void consumeMessage() throws JMSException{
	this.consumer.setup();
	this.consumer.consumeMessage();
    }

}