package com.ncr.serverchain.topicactor.consumer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.advisory.AdvisorySupport;
import org.springframework.stereotype.Component;

import com.ncr.serverchain.message.listener.ChildrenSubscribersListener;
/**
 * Class that consume messages from an administrative queue where the information
 * of the subscribers is
 * @author Otto Abreu
 *
 */
@Component("subscribersTopicConsumer")
public class SubscribersTopicConsumer extends TopicConsumer {

    @Override
    public void setup() throws JMSException {
	if (!this.isConnected()) {

	    this.setupConnectionFactoryAndSession();
	    Destination consumerTopic = AdvisorySupport
		    .getConnectionAdvisoryTopic();
	    MessageConsumer messageConsumer = this.createMessageConsumer(
		    this.getSession(), (Topic) consumerTopic);
	    this.setMessageConsumer(messageConsumer);
	    this.setConnected(true);
	}
    }

    @Override
    protected MessageConsumer createMessageConsumer(Session session, Topic topic)
	    throws JMSException {

	MessageConsumer consumerAdvisory = session.createConsumer(topic);
	return consumerAdvisory;
    }

    @Override
    protected String getSubscriberId() {
	
	return this.getClientId(LOCAL_MONITOR_SUBSCRIBER_BASE_ID);
    }

    @Override
    protected int getBrokerType() {

	return USE_LOCAL_BROKER_URL;
    }

    @Override
    protected MessageListener getSpecificListener() {

	ChildrenSubscribersListener stl = (ChildrenSubscribersListener) this
		.getMessageListenerFromSpringContext(this.springContext,
			ChildrenSubscribersListener.class);
	return stl;
    }

    @Override
    protected String getTopicName() {

	throw new UnsupportedOperationException(
		"This class uses AdvisorySupport.getConnectionAdvisoryTopic() to get the topic, calling this method is not valid");
    }
    

}
