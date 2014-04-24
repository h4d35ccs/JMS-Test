package com.ncr.ATMMonitoring.serverchain.executer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.ChainLinkInformation;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.OutgoingMessage;
import com.ncr.ATMMonitoring.serverchain.topicactor.producer.GenericMessageProducer;

@Component
public class ProducerOutgoingTestExecuter {

    @Value("${jms.localbroker.url}")
    private String localUrl;

    @Autowired
    private ChainLinkInformation chainLinkPosition;

    @Autowired
    private GenericMessageProducer outgoingProducer;

    // private static final Logger logger = Logger
    // .getLogger(ProducerTestExecuter.class);

    private int count = 0;

    @Scheduled(cron = "3 * * * * *")
    public void runProducer() {

	if (!chainLinkPosition.hasParentNode()) {

	    OutgoingMessage outMessage = new OutgoingMessage(
		    "Outgoing message to send from: " + localUrl, this.count++);

	    this.outgoingProducer.sendMessage(outMessage);
	}
    }
}
