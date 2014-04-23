/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.executer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

    @Autowired
    private ChainLinkInformation chainLinkPosition;

    @Autowired
    private ChildrenLinkListHandler childrenLinkListHandler;

    @Autowired
    protected ApplicationContext springContext;

    private Map<String, TopicConsumer> destinationsAndConsumerInstance = new HashMap<String, TopicConsumer>();

    private static final Logger logger = Logger
	    .getLogger(IncomingMessageConsumerExecuter.class);

    @Scheduled(cron = "1 * * * * *")
    public void runIncomingConsumer() {

	boolean isLeafNode = this.chainLinkPosition.isLeaf();
	logger.debug("is leaf node?: " + isLeafNode);

	if (!isLeafNode) {
	    try {

		Set<String> childrenBrokerIp = this.childrenLinkListHandler
			.getChildrenSubscribed();

		logger.debug("is not leaf, is going to subscribe to remote children "
			+ childrenBrokerIp);

		this.consumeMessagesFromChildren(childrenBrokerIp);

	    } catch (JMSException e) {
		logger.error(e.getMessage(), e);
	    }

	}

    }

    private void consumeMessagesFromChildren(Set<String> childrenBrokerIp)
	    throws JMSException {

	for (String childBrokerIp : childrenBrokerIp) {

	    this.consumeMessageFromChild(childBrokerIp);
	}

    }

    private void consumeMessageFromChild(String childBrokerIp)
	    throws JMSException {

	IncomingMessageConsumer incomingConsumer = (IncomingMessageConsumer) this
		.getTopicConsumerInstance(childBrokerIp);
	
	String completeBrokerURL = this.chainLinkPosition
		.generateRemoteBrokerUrl(childBrokerIp);

	logger.debug("remote child to consume from: " + completeBrokerURL);
	incomingConsumer.setRemoteChildBrokerUrl(completeBrokerURL);
	incomingConsumer.setup();
	incomingConsumer.consumeMessage();
    }

    private TopicConsumer getTopicConsumerInstance(String childBrokerIp) {

	TopicConsumer topicConsumer = null;

	if (!this.destinationsAndConsumerInstance.containsKey(childBrokerIp)) {
	    logger.debug("TopicConsumer from spring context");
	    topicConsumer = this.springContext
		    .getBean(IncomingMessageConsumer.class);
	    this.destinationsAndConsumerInstance.put(childBrokerIp,
		    topicConsumer);

	} else {
	    logger.debug("TopicConsumer from map");
	    topicConsumer = this.destinationsAndConsumerInstance
		    .get(childBrokerIp);
	}

	return topicConsumer;
    }
}
