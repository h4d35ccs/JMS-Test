package com.ncr.ATMMonitoring.serverchain.topicactors.consumer;

import javax.annotation.PostConstruct;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.CommandTypes;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.RemoveInfo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.topicactors.TopicActor;

@Component
public class Advisory extends TopicActor{

    protected static String brokerURL = "tcp://153.57.97.176:61617";

    protected static transient ConnectionFactory factory;
    protected transient Connection connection;
    protected transient Session session;
    private boolean connected;
    
    
    public void setup() throws Exception {
	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
		this.getLocalBrokerUrl());
	connectionFactory.setClientID("localhostMonitor");
	    this.connection = this
		    .getAndStartJMSConnection(connectionFactory);
	  
	  this.session = this.createJMSSession(this.connection);
	  this.connected = true;
    }

    @Scheduled(cron = "1 * * * * *")
    public void checkConsumers() throws Exception {
	
	if(!this.connected){
	    this.setup();
	}
	  
	ConsumerAdvisoryListener listener = this.new ConsumerAdvisoryListener();

	 Topic topic = this.getTopicFromSession(session,
		    this.getOutgoingTopicName());
	 
	
	Destination consumerTopic = AdvisorySupport.getConnectionAdvisoryTopic();
	
	MessageConsumer consumerAdvisory = session
		.createConsumer(consumerTopic);
	consumerAdvisory.setMessageListener(listener);

    }

    public Session getSession() {
	return session;
    }

    public class ConsumerAdvisoryListener implements MessageListener {
	public void onMessage(Message message) {
	    ActiveMQMessage msg = (ActiveMQMessage) message;
	    DataStructure ds = msg.getDataStructure();
	

	    if (ds != null) {
		switch (ds.getDataStructureType()) {
		case CommandTypes.CONNECTION_INFO:
		   
		    ConnectionInfo connectionInfo = (ConnectionInfo) ds;
		   if(connectionInfo.getClientId().startsWith("subscriber")){
		       System.out.println("Consumer "
				    + connectionInfo.getClientId());
		   }    
		    
		    break;
		    
		default:
		  
		}
	    } else {
		System.out.println("No data structure provided");
	    }
	}
    }
       

  

}
