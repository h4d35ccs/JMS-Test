package com.ncr.ATMMonitoring.serverchain.executer;

import javax.annotation.Resource;
import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.ChainLinkInformation;
import com.ncr.ATMMonitoring.serverchain.topicactor.consumer.TopicConsumer;

@Component("ConsumerExecuter")
public class OutgoingMessageConsumerExecuter {

    @Resource(name="outgoingMessageConsumer")
    private TopicConsumer consumer;
    
    @Autowired
    private ChainLinkInformation chainLinkPosition;

    private static final Logger logger = Logger
	    .getLogger(OutgoingMessageConsumerExecuter.class);

    @Scheduled(cron = "1 * * * * *")
    public void runConsumer() {
	logger.debug("cheching");

	boolean hasParentAndShouldLaunchConsumer = this.chainLinkPosition
		.hasParentNode();

	if (hasParentAndShouldLaunchConsumer) {
	    try {
		this.consumer.setup();
		this.consumer.consumeMessage();
	    } catch (JMSException e) {
		logger.error(e.getMessage(),e);
	    }

	}

    }

}
