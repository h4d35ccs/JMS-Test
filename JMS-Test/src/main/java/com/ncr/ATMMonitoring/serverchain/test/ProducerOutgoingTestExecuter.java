package com.ncr.ATMMonitoring.serverchain.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.MessagePublisher;
import com.ncr.ATMMonitoring.serverchain.NodeInformation;
import com.ncr.ATMMonitoring.serverchain.message.specific.outgoing.UpdateDataRequest;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageWrapper;

@Component
public class ProducerOutgoingTestExecuter {

    @Autowired
    private NodeInformation nodeInformation;

    @Autowired
    private MessagePublisher messagePublisher;

    private int count = 0;

    @Scheduled(fixedDelay = 40000)
    public void runProducer() {

	if (!nodeInformation.hasParentNode()) {

	    UpdateDataRequest udr = new UpdateDataRequest("192.168.1.1", 3);

	    messagePublisher.publishOutgoingMessage(this.count++,
		    MessageWrapper.DEFAULT_OUTGOINGMESSAGE_INNER_MESSAGE + nodeInformation.getLocalUrl(), udr);

	}
    }
}
