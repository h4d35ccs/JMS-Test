package com.ncr.ATMMonitoring.serverchain.topicactors;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Value;

public class TopicActor {

    @Value("${jms.parent.outgoing.topic.url:}")
    private String parentOutgoingTopicUrl;

    @Value("${jms.outgoing.topic.name:}")
    private String outgoingTopicName;

    @Value("${jms.localbroker.url:}")
    private String localBrokerUrl;

    protected Connection getAndStartJMSConnection(ConnectionFactory factory)
	    throws JMSException {

	Connection connection = factory.createConnection();
	connection.start();
	return connection;
    }

    protected Session createJMSSession(Connection connection)
	    throws JMSException {

	return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    protected Topic getTopicFromSession(Session session, String topicName)
	    throws JMSException {

	return session.createTopic(outgoingTopicName);
    }

    protected String getParentOutgoingTopicUrl() {

	return parentOutgoingTopicUrl;
    }

    protected String getOutgoingTopicName() {
	return outgoingTopicName;
    }

    protected void closeConnection(Connection con) throws JMSException {
	con.close();
    }

    protected String getNodeLocalAddress() {

	String localAddress = "localhost";

	if (this.localBrokerUrl != null && !this.localBrokerUrl.equals("")) {

	    String[] addressSplitByProtocol = this.localBrokerUrl.split("://");
	    String adressWithPort = addressSplitByProtocol[1];
	    // String[] addressSplitByPort = adressWithPort.split(":");
	    // localAddress = addressSplitByPort[0];
	    localAddress = adressWithPort;
	}

	return localAddress;
    }

    public String getLocalBrokerUrl() {
        return localBrokerUrl;
    }
    
    

}
