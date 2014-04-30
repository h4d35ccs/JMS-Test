package com.ncr.ATMMonitoring.serverchain.executer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.MessagePublisher;
import com.ncr.ATMMonitoring.serverchain.NodeInformation;
import com.ncr.ATMMonitoring.serverchain.message.specific.incoming.UpdateDataResponse;

@Component
public class ProducerIncomingTestExecuter {

    @Autowired
    private NodeInformation nodeInformation;

    @Autowired
    private MessagePublisher messagePublisher;

    private int count = 0;

    @Scheduled(cron = "3 * * * * *")
    public void runProducer() {

	if (nodeInformation.hasParentNode()) {
	    this.messagePublisher.publishIncomingMessage(this.count++,
		    "Incoming message to send from: " + nodeInformation.getLocalBrokerUrl(),
		    new UpdateDataResponse());
	}
    }
}
