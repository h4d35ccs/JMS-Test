package com.ncr.ATMMonitoring.serverchain.topicactors.consumer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.advisory.AdvisorySupport;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.listener.ChildrenSubscribersListener;

@Component
public class Advisory extends TopicConsumer {

 
    @Scheduled(cron = "1 * * * * *")
    public void checkConsumers() throws Exception {

	this.setup();
	System.out.println("consuming advisory msg");
	this.consumeMessage();
    }
    @Override
    public void setup() throws JMSException {
	if (!this.isConnected()) {

	    this.setupConnectionFactoryAndSession();
	    Destination consumerTopic = AdvisorySupport.getConnectionAdvisoryTopic();
	    MessageConsumer messageConsumer = this.createMessageConsumer(this.getSession(),(Topic)consumerTopic);
	    this.setMessageConsumer(messageConsumer);
	    this.setConnected(true);
	}
    }
    
    @Override
    protected MessageConsumer createMessageConsumer(Session session, Topic topic)
	    throws JMSException {

	MessageConsumer consumerAdvisory = session
		.createConsumer(topic);
	return consumerAdvisory;
    }

    @Override
    protected String getSubscriberId() {

	return this.getClientId(LOCAL_MONITOR_SUBSCRIBER_BASE_ID);
    }

    @Override
    protected int getBrokerType() {
	// TODO Auto-generated method stub
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
	return null;
    }

}
