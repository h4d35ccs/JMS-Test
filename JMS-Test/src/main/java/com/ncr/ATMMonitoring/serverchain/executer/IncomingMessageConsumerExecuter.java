/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.executer;

import java.util.Set;

import javax.annotation.Resource;
import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.ChainLinkInformation;
import com.ncr.ATMMonitoring.serverchain.ChildrenLinkListHandler;
import com.ncr.ATMMonitoring.serverchain.topicactor.consumer.TopicConsumer;
import com.ncr.ATMMonitoring.serverchain.topicactor.consumer.incoming.IncomingMessageConsumer;

/**
 * @author Otto Abreu
 *
 */
@Component
public class IncomingMessageConsumerExecuter {
    
    @Resource(name="incomingMessageConsumer")
    private TopicConsumer consumer;
    
    @Autowired
    private ChainLinkInformation chainLinkPosition;
    
    @Autowired
    private ChildrenLinkListHandler childrenLinkListHandler;
    
    private static final Logger logger = Logger
	    .getLogger(IncomingMessageConsumerExecuter.class);
    
    @Scheduled(cron = "1 * * * * *")
    public void runIncomingConsumer() {
	logger.debug("cheching if is leaf");

	boolean isNotLeafNode = this.chainLinkPosition.isLeaf();

	if (!isNotLeafNode) {
	    try {
		
		
		Set<String> childrenBrokerIp = this.childrenLinkListHandler.getChildrenSubscribed();
		logger.debug("is not leaf, is going to subscribe to remote children "+childrenBrokerIp);
		
		for(String brokerIp : childrenBrokerIp){
		   
		    logger.debug("remote child: "+brokerIp);
		    IncomingMessageConsumer incomingConsumer = (IncomingMessageConsumer)this.consumer;
		    String completeBrokerURL =  this.chainLinkPosition.generateRemoteBrokerUrl(brokerIp);
		    logger.debug("remote child: "+completeBrokerURL);
		    incomingConsumer.setRemoteChildBrokerUrl(completeBrokerURL);
		    incomingConsumer.setup();
		    incomingConsumer.consumeMessage();
		}
		
	    } catch (JMSException e) {
		logger.error(e.getMessage(),e);
	    }

	}

    }
}
