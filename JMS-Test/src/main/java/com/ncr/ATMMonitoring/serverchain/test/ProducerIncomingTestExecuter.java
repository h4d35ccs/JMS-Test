package com.ncr.ATMMonitoring.serverchain.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.specific.incoming.UpdateDataResponse;
import com.ncr.serverchain.MessagePublisher;
import com.ncr.serverchain.NodeInformation;
import com.ncr.serverchain.message.wrapper.MessageWrapper;

@Component
public class ProducerIncomingTestExecuter {

    @Autowired
    private NodeInformation nodeInformation;

    @Autowired
    private MessagePublisher messagePublisher;

    private int count = 0;

//    @Scheduled(cron = "3 * * * * *")
    public void runProducer() {

	if (nodeInformation.hasParentNode()) {
	    this.messagePublisher.publishIncomingMessage(this.count++,
		    MessageWrapper.DEFAULT_INCOMINGMESSAGE_INNER_MESSAGE + nodeInformation.getLocalUrl(),
		    new UpdateDataResponse());
	}
    }
}
