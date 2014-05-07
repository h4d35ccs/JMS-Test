package com.ncr.ATMMonitoring.serverchain.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.MessagePublisher;
import com.ncr.ATMMonitoring.serverchain.NodeInformation;
import com.ncr.ATMMonitoring.serverchain.message.specific.incoming.UpdateRouterTable;

@Component
public class ProducerIncomingUpdateTableTestExecuter {

    @Autowired
    private NodeInformation nodeInformation;

    @Autowired
    private MessagePublisher messagePublisher;
    
    private static final String URL_PORT_TO_TEST="61622";

    private int count = 0;

   @Scheduled(cron = "5 * * * * *")
    public void runProducer() {
	String localUrl = nodeInformation.getLocalBrokerUrl();
	
	if (localUrl.contains(URL_PORT_TO_TEST) && this.count < 3) {
	    System.out.println("Is going to update table ");
	    UpdateRouterTable updateInfo = new UpdateRouterTable(3, this.nodeInformation.getLocalUrl());
	    this.messagePublisher.publishIncomingMessage(this.count++,
		    "Incoming message to update table send from: " + nodeInformation.getLocalBrokerUrl(),
		    updateInfo );
	}
    }
    
}
