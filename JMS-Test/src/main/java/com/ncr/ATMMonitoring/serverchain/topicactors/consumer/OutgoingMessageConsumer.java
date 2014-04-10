package com.ncr.ATMMonitoring.serverchain.topicactors.consumer;

import javax.annotation.PreDestroy;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.topicactors.TopicActor;

@Component("OutgoingConsumer")
public class OutgoingMessageConsumer extends TopicActor {

    private static final Logger logger = Logger
	    .getLogger(OutgoingMessageConsumer.class);

    private MessageConsumer messageConsumer;
   
    private Session session;

    private boolean connected = false;
    
    @Autowired
    private ApplicationContext springContext;

    
    public void getOutgoingMessageFromTopic() {
	try {
	    
	    setListnerInMsgConsumer();

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
    
   
    private void setListnerInMsgConsumer() throws JMSException{
	 logger.debug("setting listner consumer");
	 
	  OutgoingMessageListener listener = springContext.getBean(OutgoingMessageListener.class);
	  this.messageConsumer.setMessageListener(listener);
	  
    }

    public void subscribeSetup() throws JMSException {

	if (!this.connected && this.hasParentNode()) {

	    ConnectionFactory connectionFactory = this
		    .createConnectionFactory();
	    
	    logger.debug("creating consumer connection");
	    
	    Connection connection = this
		    .getAndStartJMSConnection(connectionFactory);

	    this.session = this.createJMSSession(connection);

	    Topic topic = this.getTopicFromSession(session,
		    this.getOutgoingTopicName());

	    this.messageConsumer = this.createMessageConsumer(this.session, topic);
	    
	    this.connected = true;
	}
    }

    @PreDestroy
    public void disconect() throws JMSException {
	this.session.close();
	this.connected = false;
    }

    private ConnectionFactory createConnectionFactory() {

	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
		this.getParentOutgoingTopicUrl());
	connectionFactory.setClientID(this.getSubscriberId());
	

	return connectionFactory;
    }

    private MessageConsumer createMessageConsumer(Session session, Topic topic)
	    throws JMSException {

	return session.createDurableSubscriber(topic,this.getSubscriberId());
    }

    private String getSubscriberId() {
	logger.debug("the subscriber id: "+this.getNodeLocalAddress());
	return "subscriber[" + this.getNodeLocalAddress() + "]";
    }

    public boolean hasParentNode() {
	boolean hasParent = false;

	if (this.getParentOutgoingTopicUrl() != null
		&& !this.getParentOutgoingTopicUrl().equals("")) {
	   
	    hasParent = true;
	}
	return hasParent;
    }

}
