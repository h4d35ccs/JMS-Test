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
 * Class that holds the generic logic to consume messages from a topic
 * Implements Template method pattern
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

    /**
     * Returns the id to be use by the subscriber
     * @return
     */
    protected abstract String getSubscriberId();

    /**
     * 
     * Returns the broker type to connect with 
     * Use {@link TopicActor#USE_EXTERNAL_BROKER_URL} to refer to an remote broker,
     *  {@link TopicActor#USE_CHILD_INCOMING_BROKER_URL} to refer to a remote child incoming broker
     *   {@link TopicActor#USE_PARENT_OUTGOING_BROKER_URL} to refer to a parent remote outgoing broker
     * @return int
     */
    protected abstract int getBrokerType();

    /**
     * Sets the listener to use in this class
     * @return MessageListener
     */
    protected abstract MessageListener getSpecificListener();

    /**
     * Returns the name of the topic to connect with
     * @return String
     */
    protected abstract String getTopicName();
    
    protected boolean CREATE_DURABLE_SUBSCRIBER = true;
    
    protected boolean CREATE_NONDURABLE_SUBSCRIBER = false;
    

    /**
     * Method that execute the logic to consume the messages in the queue
     */
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

    /**
     * Setup the class to consume messages.
     * Uses the getBrokerType to know to whom this class with connect
     * @throws JMSException
     */
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

    /**
     *  Setup the class to consume messages.
     *  Receives the url to connect with
     * @param remoteBrokerUrl
     * @throws JMSException
     */
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

    /**
     * Creates the durable or non durable  message consumer 
     * @param session
     * @param topic
     * @return
     * @throws JMSException
     */
    protected MessageConsumer createMessageConsumer(Session session, Topic topic)
	    throws JMSException {
	MessageConsumer consumer = null;
	
	if(createDurableSubscriber()){
	    
	    consumer = session.createDurableSubscriber(topic, this.getSubscriberId());
	    
	}else{
	    
	    consumer = session.createConsumer(topic);
	}
	return consumer;
    }
    /**
     * Returns true if this class will create durable subscribers,
     * false will create non durable subscribers.
     * 
     * Overwrite this method to change default behavior
     * @return boolean
     */
    protected boolean createDurableSubscriber(){
	return CREATE_DURABLE_SUBSCRIBER;
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
