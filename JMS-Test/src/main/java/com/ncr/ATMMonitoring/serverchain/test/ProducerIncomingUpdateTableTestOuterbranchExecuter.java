package com.ncr.ATMMonitoring.serverchain.test;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.specific.incoming.UpdateRouterTable;
import com.ncr.serverchain.MessagePublisher;
import com.ncr.serverchain.NodeInformation;

@Component
public class ProducerIncomingUpdateTableTestOuterbranchExecuter {

    @Autowired
    private NodeInformation nodeInformation;

    @Autowired
    private MessagePublisher messagePublisher;
    
    private static final String URL_PORT_TO_TEST="61626";
    
    private static final Logger logger = Logger
	    .getLogger(ProducerIncomingUpdateTableTestOuterbranchExecuter.class);

    private int count = 0;

    @Scheduled(fixedDelay = 8000)
    public void runProducer() {
	String localUrl = nodeInformation.getLocalBrokerUrl();
	
	if (localUrl.contains(URL_PORT_TO_TEST) && (this.count > 3 && this.count < 6)) {
	    logger.debug("changing router table for:"+URL_PORT_TO_TEST);
	    UpdateRouterTable updateInfo = new UpdateRouterTable(3, this.nodeInformation.getLocalUrl());
	    this.messagePublisher.publishIncomingMessage(this.count,
		    "Incoming message to update table send from: " + nodeInformation.getLocalBrokerUrl(),
		    updateInfo );
	}
	
	this.count++;
    }
    
}
