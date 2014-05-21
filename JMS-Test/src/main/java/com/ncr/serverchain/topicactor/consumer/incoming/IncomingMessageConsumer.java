/**
 * 
 */
package com.ncr.serverchain.topicactor.consumer.incoming;

import javax.jms.MessageListener;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ncr.serverchain.message.listener.IncomingMessageListener;
import com.ncr.serverchain.topicactor.consumer.TopicConsumer;

/**
 * @author Otto Abreu
 * 
 */
@Component("incomingMessageConsumer")
@Scope("prototype")
public class IncomingMessageConsumer extends TopicConsumer {



     @Override
    protected String getSubscriberId() {
	
	return this.getClientId(PARENT_SUBSCRIBER_BASE_ID);
    }

    @Override
    protected int getBrokerType() {

	return USE_EXTERNAL_BROKER_URL;

    }

    @Override
    protected MessageListener getSpecificListener() {
	return this.getMessageListenerFromSpringContext(this.springContext,
		IncomingMessageListener.class);
    }

    @Override
    protected String getTopicName() {
	return this.getIncomingTopicName();
    }

}
