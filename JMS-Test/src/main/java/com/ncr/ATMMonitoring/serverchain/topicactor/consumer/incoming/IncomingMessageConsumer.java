/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.topicactor.consumer.incoming;

import javax.jms.MessageListener;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.listener.IncomingMessageListener;
import com.ncr.ATMMonitoring.serverchain.topicactor.consumer.TopicConsumer;

/**
 * @author Otto Abreu
 * 
 */
@Component("incomingMessageConsumer")
@Scope("prototype")
public class IncomingMessageConsumer extends TopicConsumer {

    private static final Logger logger = Logger
	    .getLogger(IncomingMessageConsumer.class);

     @Override
    protected String getSubscriberId() {
	logger.debug("the subscriber parent id: " + this.getNodeLocalAddress());
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
	// TODO Auto-generated method stub
	return this.getIncomingTopicName();
    }

}
