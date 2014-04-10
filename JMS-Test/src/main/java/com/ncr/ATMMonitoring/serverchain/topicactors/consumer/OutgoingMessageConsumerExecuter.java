package com.ncr.ATMMonitoring.serverchain.topicactors.consumer;

import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("ConsumerExecuter")
public class OutgoingMessageConsumerExecuter {

    @Autowired
    private OutgoingMessageConsumer consumer;

    private static final Logger logger = Logger
	    .getLogger(OutgoingMessageConsumerExecuter.class);

    @Scheduled(cron = "1 * * * * *")
    public void runConsumer() {
	logger.debug("cheching");

	boolean hasParentAndShouldLaunchConsumer = this.consumer
		.hasParentNode();

	if (hasParentAndShouldLaunchConsumer) {
	    try {
		this.consumer.subscribeSetup();
		this.consumer.getOutgoingMessageFromTopic();
	    } catch (JMSException e) {
		logger.error(e.getMessage(),e);
	    }

	}

    }

}
