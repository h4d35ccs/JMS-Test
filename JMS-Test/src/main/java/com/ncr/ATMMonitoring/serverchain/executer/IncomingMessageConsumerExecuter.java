/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.executer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.NodeInformation;
import com.ncr.ATMMonitoring.serverchain.ChildrenLinkListHandler;
import com.ncr.ATMMonitoring.serverchain.NodePosition;
import com.ncr.ATMMonitoring.serverchain.topicactor.consumer.TopicConsumer;
import com.ncr.ATMMonitoring.serverchain.topicactor.consumer.incoming.IncomingMessageConsumer;

/**
 * class that checks the incoming topic and consumes the messages that arrives
 * 
 * @author Otto Abreu
 * 
 */
@Component
public class IncomingMessageConsumerExecuter {

    @Autowired
    private NodeInformation nodePosition;

    @Autowired
    private ChildrenLinkListHandler childrenLinkListHandler;

    @Autowired
    protected ApplicationContext springContext;

    private Map<String, TopicConsumer> destinationsAndConsumerInstance = new HashMap<String, TopicConsumer>();

    private static final Logger logger = Logger
	    .getLogger(IncomingMessageConsumerExecuter.class);

    /**
     * checks for a new message in the incoming topic
     */
    @Scheduled(cron = "1 * * * * *")
    public void runIncomingConsumer() {

	if (isNotLeaf()) {
	    try {

		Set<String> childrenBrokerIp = this.childrenLinkListHandler
			.getChildrenSubscribed();

		logger.debug("is not leaf, is going to subscribe to remote children "
			+ childrenBrokerIp);

		this.consumeMessagesFromChildren(childrenBrokerIp);

	    } catch (JMSException e) {

		this.hanndleJMSException(e);

	    }

	}

    }

    private boolean isNotLeaf() {
	NodePosition position = this.nodePosition.getNodePosition();
	logger.debug("is leaf node?: " + position);

	if (!(position.equals(NodePosition.LEAF_NODE))) {
	    return true;

	} else {

	    return false;
	}
    }

    private void consumeMessagesFromChildren(Set<String> childrenBrokerIp)
	    throws JMSException {

	for (String childBrokerIp : childrenBrokerIp) {

	    this.consumeMessageFromChild(childBrokerIp);
	}

    }

    private void hanndleJMSException(JMSException e) {
	try {

	    handdleConsumerInUseForClientException(e);

	} catch (JMSException e1) {
	    logger.error("can not recovery from:" + e.getMessage(), e1);

	}

    }

    private void handdleConsumerInUseForClientException(JMSException e)
	    throws JMSException {

	if (e.getMessage().contains("Durable consumer is in use for client")) {

	    this.disconectAllConsumers();

	} else {
	    logger.error(e.getMessage(), e);
	}
    }

    private void disconectAllConsumers() throws JMSException {

	for (Map.Entry<String, TopicConsumer> entry : this.destinationsAndConsumerInstance
		.entrySet()) {
	    entry.getValue().disconect();
	}
    }

    private void consumeMessageFromChild(String childBrokerIp)
	    throws JMSException {

	IncomingMessageConsumer incomingConsumer = (IncomingMessageConsumer) this
		.getTopicConsumerInstance(childBrokerIp);

	String completeBrokerURL = this.nodePosition
		.generateRemoteBrokerUrl(childBrokerIp);

	this.consumeMessage(completeBrokerURL, incomingConsumer);
    }
    
    private void consumeMessage(String completeBrokerURL,IncomingMessageConsumer incomingConsumer ) throws JMSException{
	logger.debug("remote child to consume from: " + completeBrokerURL);
	incomingConsumer.setup(completeBrokerURL);
	incomingConsumer.consumeMessage();
    }
    /**
     * <pre>
     * Get an instance of  TopicConsumer.
     * 
     * This is needed because each instance holds the connection to the remote topic, if another instance with the same id tries to connect it will rise an exception.
     * For that reason each instance of TopicConsumer is hold in a Map 
     * @param childBrokerIp
     * @return TopicConsumer
     * </pre>
     */
    private synchronized TopicConsumer getTopicConsumerInstance(
	    String childBrokerIp) {

	TopicConsumer topicConsumer = null;

	if (!this.destinationsAndConsumerInstance.containsKey(childBrokerIp)) {

	    this.getTopicConsumerFromSpringContextAndPutItInMap(childBrokerIp);

	} else {
	    
	    this.getTopicConsumerFromMap(childBrokerIp);
	}

	return topicConsumer;
    }

    private TopicConsumer getTopicConsumerFromSpringContextAndPutItInMap(String childBrokerIp) {

	logger.debug("TopicConsumer from spring context");
	TopicConsumer topicConsumer = this.springContext
		.getBean(IncomingMessageConsumer.class);
	this.destinationsAndConsumerInstance.put(childBrokerIp, topicConsumer);

	return topicConsumer;

    }
    
    private TopicConsumer getTopicConsumerFromMap(String childBrokerIp){
	logger.debug("TopicConsumer from map");
	   return this.destinationsAndConsumerInstance
		    .get(childBrokerIp);
    }

}
