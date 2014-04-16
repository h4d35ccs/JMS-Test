package com.ncr.ATMMonitoring.serverchain.topicactors.producer;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.topicactor.TopicActor;

@Component("outgoingProducer")
public class OutgoingMessageProducer  extends TopicActor  {

    @Autowired
    private ActiveMQConnectionFactory localConnectionFactory;

    private static final Logger logger = Logger
	    .getLogger(OutgoingMessageProducer.class);

    private Session session;

    private MessageProducer producer;

    private Connection connection;
    
    public void sendMessage(Serializable message) {

	try {

	    logger.debug("message to send:" + message);

	    this.setupLocalConectionToBroker();

	    ObjectMessage messageToSend = this.createMessageFromSession(
		    session, message);

	    this.sendMessage(producer, messageToSend);

	    this.closeConnection(connection);

	    logger.debug("message sent" + message);
	} catch (JMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
    
    private ObjectMessage createMessageFromSession(Session session,
	    Serializable msg) throws JMSException {

	return session.createObjectMessage(msg);
    }

    private void setupLocalConectionToBroker() throws JMSException {

	this.connection = this.getAndStartLocalConnectionJMSConnection();

	this.session = this.createJMSSession(this.connection);

	Topic topic = this.getTopicFromSession(session,
		this.getOutgoingTopicName());

	this.producer = this.createMsgProducerFromSession(this.session, topic);
    }

    private Connection getAndStartLocalConnectionJMSConnection()
	    throws JMSException {
	return this.getAndStartJMSConnection(this.localConnectionFactory);

    }

    private MessageProducer createMsgProducerFromSession(Session session,
	    Topic topic) throws JMSException {

	return session.createProducer(topic);
    }

    

    private void sendMessage(MessageProducer producer, ObjectMessage message)
	    throws JMSException {

	producer.send(message);
    }

}
