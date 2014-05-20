package com.ncr.ATMMonitoring.serverchain.topicactor.consumer.outgoing;

import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.listener.OutgoingMessageListener;
import com.ncr.ATMMonitoring.serverchain.topicactor.consumer.TopicConsumer;

@Component("outgoingMessageConsumer")
public  class OutgoingMessageConsumer extends TopicConsumer {


 
    protected String getSubscriberId() {

	return this.getClientId(SUBSCRIBER_BASE_ID);
    }

    @Override
    protected int getBrokerType() {

	return USE_PARENT_OUTGOING_BROKER_URL;
    }

    @Override
    protected MessageListener getSpecificListener() {
	OutgoingMessageListener listener = (OutgoingMessageListener) this
		.getMessageListenerFromSpringContext(this.springContext,
			OutgoingMessageListener.class);
	return listener;
    }

    @Override
    protected String getTopicName() {
	
	return this.getOutgoingTopicName();
    }


}
