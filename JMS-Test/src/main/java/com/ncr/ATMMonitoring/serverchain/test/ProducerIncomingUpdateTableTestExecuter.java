package com.ncr.ATMMonitoring.serverchain.test;

import org.apache.log4j.Logger;
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
    

    private static final Logger logger = Logger
	    .getLogger(ProducerIncomingUpdateTableTestExecuter.class);

    private int count = 0;

//    @Scheduled(cron="*/20 * * * * ?")
    public void runProducer() {
	String localUrl = nodeInformation.getLocalBrokerUrl();
	logger.debug(" cont change from local leaf (2<x<5):"+this.count);
	if (localUrl.contains(URL_PORT_TO_TEST) && (this.count > 2 && this.count < 5)) {
	    logger.debug("Is going to update table "+URL_PORT_TO_TEST);
	    UpdateRouterTable updateInfo = new UpdateRouterTable(3, this.nodeInformation.getLocalUrl());
	    this.messagePublisher.publishIncomingMessage(this.count,
		    "Incoming message to update table send from: " + nodeInformation.getLocalBrokerUrl(),
		    updateInfo );
	}
	this.count++;
    }
    
}
