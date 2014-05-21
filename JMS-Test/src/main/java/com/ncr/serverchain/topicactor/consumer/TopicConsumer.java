/**
 * 
 */
package com.ncr.serverchain.topicactor.consumer;

import javax.annotation.PreDestroy;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ncr.serverchain.topicactor.TopicActor;

/**
 * @author Otto Abreu
 * 
 */
@Component
public abstract class TopicConsumer extends TopicActor {

    private Session session;

    private boolean connected = false;

    private MessageConsumer messageConsumer;

    private static final Logger logger = Logger.getLogger(TopicConsumer.class);

    @Autowired
    protected ApplicationContext springContext;

    protected abstract String getSubscriberId();

    protected abstract int getBrokerType();

    protected abstract MessageListener getSpecificListener();

    protected abstract String getTopicName();

    public void consumeMessage() {

	try {

	    this.setMessageListener();

	} catch (JMSException e) {
	    try {
		logger.warn("exception thrown while processing the message", e);

		this.disconect();

	    } catch (JMSException e1) {
		logger.warn(
			"exception thrown while closing the conection after exception",
			e);
	    }
	}
    }

    protected void setMessageListener() throws JMSException {
	this.messageConsumer.setMessageListener(this.getSpecificListener());
    }

    public void setup() throws JMSException {

	if (!this.connected) {

	    this.setupConnectionFactoryAndSession();

	    Topic topic = this
		    .getTopicFromSession(session, this.getTopicName());

	    this.messageConsumer = this.createMessageConsumer(this.session,
		    topic);

	    this.connected = true;
	}
    }

    public void setup(String remoteBrokerUrl) throws JMSException {
	this.setExternalBrokerUrl(remoteBrokerUrl);
	this.setup();
    }

    protected void setupConnectionFactoryAndSession() throws JMSException {

	ConnectionFactory connectionFactory = this.createConnectionFactory(this
		.getBrokerType());


	Connection connection = this
		.getAndStartJMSConnection(connectionFactory);

	this.session = this.createJMSSession(connection);

    }

    private ConnectionFactory createConnectionFactory(int brokerType) {

	ConnectionFactory connectionFactory = this.getConnectionFactory(
		this.getSubscriberId(), brokerType);
	return connectionFactory;
    }

    protected MessageConsumer createMessageConsumer(Session session, Topic topic)
	    throws JMSException {
	return session.createDurableSubscriber(topic, this.getSubscriberId());
    }

    @PreDestroy
    public void disconect() throws JMSException {
	if (this.session != null) {
	    this.session.close();
	}
	this.connected = false;
    }

    protected MessageListener getMessageListenerFromSpringContext(
	    ApplicationContext springContext, Class<?> listenerClass) {
	return (MessageListener) springContext.getBean(listenerClass);

    }

    protected void setMessageConsumer(MessageConsumer messageConsumer) {
	this.messageConsumer = messageConsumer;
    }

    protected Session getSession() {
	return session;
    }

    protected void setSession(Session session) {
	this.session = session;
    }

    protected boolean isConnected() {
	return connected;
    }

    protected void setConnected(boolean connected) {
	this.connected = connected;
    }

}
