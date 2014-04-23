package com.ncr.ATMMonitoring.serverchain.executer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.ChainLinkInformation;
import com.ncr.ATMMonitoring.serverchain.message.IncomingMessage;
import com.ncr.ATMMonitoring.serverchain.topicactor.producer.GenericMessageProducer;

@Component
public class ProducerIncomingTestExecuter {
    
    @Value("${jms.localbroker.url}")
    private String localUrl;

    @Autowired
    private ChainLinkInformation chainLinkPosition;

    @Autowired
    private GenericMessageProducer incomingMessageProducer;

//    private static final Logger logger = Logger
//	    .getLogger(ProducerTestExecuter.class);

    private int count = 0;

    @Scheduled(cron = "3 * * * * *")
    public void runProducer() {

	

	if (chainLinkPosition.hasParentNode()) {

	    IncomingMessage incomingMessage = new IncomingMessage(
		    "Incoming message to send from: " + localUrl, this.count++,
		    new Date());

	    this.incomingMessageProducer.sendMessage(incomingMessage);
	}
    }
}
