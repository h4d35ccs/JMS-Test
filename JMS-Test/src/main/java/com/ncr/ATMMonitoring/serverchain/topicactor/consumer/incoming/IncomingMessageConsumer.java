/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.topicactor.consumer.incoming;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.listener.IncomingMessageListener;
import com.ncr.ATMMonitoring.serverchain.topicactor.consumer.TopicConsumer;

/**
 * @author Otto Abreu
 * 
 */
@Component("incomingMessageConsumer")
public class IncomingMessageConsumer extends TopicConsumer {

    private static final Logger logger = Logger
	    .getLogger(IncomingMessageConsumer.class);

    private String remoteChildBrokerUrl;
    
    
    @Override
    protected void setupConnectionFactoryAndSession() throws JMSException {

	ConnectionFactory connectionFactory = this.getConnectionFactory(
		this.getSubscriberId(), this.remoteChildBrokerUrl);
	logger.debug("creating incoming consumer connection");

	Connection connection = this
		.getAndStartJMSConnection(connectionFactory);

	this.setSession(this.createJMSSession(connection));
    }

    @Override
    protected String getSubscriberId() {
	logger.debug("the subscriber parent id: " + this.getNodeLocalAddress());
	return this.getClientId(PARENT_SUBSCRIBER_BASE_ID);
    }

    @Override
    protected int getBrokerType() {
	// not in use the incoming uses several urls
	// this method is not expected to be called
	throw new UnsupportedOperationException("the incoming Msg consumer does not use a broker type");

    }

    @Override
    protected MessageListener getSpecificListener() {
	return this.getMessageListenerFromSpringContext(this.springContext,IncomingMessageListener.class);
    }

    @Override
    protected String getTopicName() {
	// TODO Auto-generated method stub
	return this.getIncomingTopicName();
    }

    public void setRemoteChildBrokerUrl(String remoteChildBrokerUrl) {
	this.remoteChildBrokerUrl = remoteChildBrokerUrl;
    }

}
