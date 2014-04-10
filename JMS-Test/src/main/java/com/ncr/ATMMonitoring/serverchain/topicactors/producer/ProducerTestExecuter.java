package com.ncr.ATMMonitoring.serverchain.topicactors.producer;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.OutgoingMessage;
import com.ncr.ATMMonitoring.serverchain.topicactors.consumer.OutgoingMessageConsumer;

@Component
public class ProducerTestExecuter {
    @Value("${jms.localbroker.url}")
    private String localUrl;

    @Autowired
    private OutgoingMessageConsumer consumer;

    @Autowired
    private OutgoingMessageProducer outgoingProducer;

    private static final Logger logger = Logger
	    .getLogger(ProducerTestExecuter.class);

    private int count = 0;

    @Scheduled(cron = "3 * * * * *")
    public void runProducer() {

	logger.debug("consumer.hasParentNode(): " + consumer.hasParentNode());

	if (!consumer.hasParentNode()) {

	    OutgoingMessage outMessage = new OutgoingMessage(
		    "message to send from: " + localUrl, this.count++,
		    new Date());

	    this.outgoingProducer.sendMessage(outMessage);
	}
    }
}
