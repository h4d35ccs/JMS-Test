package com.ncr.ATMMonitoring.serverchain.topicactor.consumer.outgoing;

import javax.jms.MessageListener;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.listener.OutgoingMessageListener;
import com.ncr.ATMMonitoring.serverchain.topicactor.consumer.TopicConsumer;

@Component("outgoingMessageConsumer")
public  class OutgoingMessageConsumer extends TopicConsumer {

    private static final Logger logger = Logger
	    .getLogger(OutgoingMessageConsumer.class);

  
    protected String getSubscriberId() {
	logger.debug("the subscriber id: " + this.getNodeLocalAddress());
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
