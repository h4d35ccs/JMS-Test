package com.ncr.ATMMonitoring.serverchain.topicactor.producer;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import com.ncr.ATMMonitoring.serverchain.message.Stamper;
import com.ncr.ATMMonitoring.serverchain.topicactor.TopicActor;


public abstract class GenericMessageProducer  extends TopicActor implements Stamper  {


    private static final Logger logger = Logger
	    .getLogger(GenericMessageProducer.class);

    private Session session;

    private MessageProducer producer;

    private Connection connection;
    
    public void sendMessage(Serializable message) {

	try {
	    
	    this.setMessageStamp(message);

	    this.setupLocalConectionToBroker();

	    ObjectMessage messageToSend = this.createMessageFromSession(
		    session, message);

	    this.sendMessage(producer, messageToSend);

	    this.closeConnection(connection);

	    logger.debug("message sent: " + message);
	
	} catch (JMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
    
    private void setupLocalConectionToBroker() throws JMSException {

	this.connection = this.getAndStartLocalConnectionJMSConnection();

	this.session = this.createJMSSession(this.connection);

	Topic topic = this.getTopicFromSession(session,
		this.getTopicName());

	this.producer = this.createMsgProducerFromSession(this.session, topic);
    }
    

    
    protected abstract String getTopicName();
    
    private ObjectMessage createMessageFromSession(Session session,
	    Serializable msg) throws JMSException {

	return session.createObjectMessage(msg);
    }

    

    private Connection getAndStartLocalConnectionJMSConnection()
	    throws JMSException {
	return this.getAndStartJMSConnection(this.getLocalConnectionFactory());

    }
    
    protected abstract ActiveMQConnectionFactory getLocalConnectionFactory();

    private MessageProducer createMsgProducerFromSession(Session session,
	    Topic topic) throws JMSException {

	return session.createProducer(topic);
    }

    

    private void sendMessage(MessageProducer producer, ObjectMessage message)
	    throws JMSException {

	producer.send(message);
    }
    
    

}
