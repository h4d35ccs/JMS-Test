package com.ncr.ATMMonitoring.serverchain.executer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.NodeInformation;
import com.ncr.ATMMonitoring.serverchain.message.specific.outgoing.UpdateDataRequest;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageWrapper;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.OutgoingMessage;
import com.ncr.ATMMonitoring.serverchain.topicactor.producer.GenericMessageProducer;

@Component
public class ProducerOutgoingTestExecuter {

    @Value("${jms.localbroker.url}")
    private String localUrl;

    @Autowired
    private NodeInformation nodePosition;

    @Autowired
    private GenericMessageProducer outgoingMessageProducer;

    // private static final Logger logger = Logger
    // .getLogger(ProducerTestExecuter.class);

    private int count = 0;

    @Scheduled(cron = "3 * * * * *")
    public void runProducer() {

	if (!nodePosition.hasParentNode()) {
	    UpdateDataRequest udr = new UpdateDataRequest("192.168.1.1", 3);
	    MessageWrapper outMessage = new OutgoingMessage(
		    "Outgoing message to send from: " + localUrl, this.count++);
	   
	    outMessage.setSpecificMessage(udr);

	    this.outgoingMessageProducer.sendMessage(outMessage);
	}
    }
}
