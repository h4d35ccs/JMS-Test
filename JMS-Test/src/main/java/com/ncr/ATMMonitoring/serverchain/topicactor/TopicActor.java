package com.ncr.ATMMonitoring.serverchain.topicactor;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.ChainLinkInformation;

@Component
public abstract class TopicActor {

    @Autowired
    private ChainLinkInformation chainLinkInformation;

    public static final String SUBSCRIBER_BASE_ID = "subscriber";

    protected static final String LOCAL_MONITOR_SUBSCRIBER_BASE_ID = "localhostMonitor";

    protected static final String PARENT_SUBSCRIBER_BASE_ID = "parentsubscriber";

    public static final String OPEN_ENCLOSING_BLOCK = "[";

    public static final String CLOSE_ENCLOSING_BLOCK = "]";

    protected static final int USE_LOCAL_BROKER_URL = 0;

    protected static final int USE_PARENT_OUTGOING_BROKER_URL = 1;

    protected static final int USE_CHILD_INCOMING_BROKER_URL = 2;

    protected ConnectionFactory getConnectionFactory(String clientId,
	    int brokerType) {

	String brokerUrl = this.getBrokerURL(brokerType);

	ConnectionFactory connectionFactory = this.createConnectionFactory(
		clientId, brokerUrl);

	return connectionFactory;
    }

    protected ConnectionFactory getConnectionFactory(String clientId,
	    String brokerUrl) {

	ConnectionFactory connectionFactory = this.createConnectionFactory(
		clientId, brokerUrl);
	return connectionFactory;
    }

    private ConnectionFactory createConnectionFactory(String clientId,
	    String brokerUrl) {
	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
		brokerUrl);
	connectionFactory.setClientID(clientId);
	return connectionFactory;
    }

    private String getBrokerURL(int brokerType) {

	String url = "";

	switch (brokerType) {
	case USE_LOCAL_BROKER_URL:
	    url = this.chainLinkInformation.getLocalBrokerUrl();
	    break;

	case USE_PARENT_OUTGOING_BROKER_URL:
	    url = this.chainLinkInformation.getParentOutgoingTopicUrl();
	    break;

	default:
	    throw new IllegalArgumentException(
		    "Unknown broker type, use TopicActor constants for defining the broker type, Received: "
			    + brokerType);
	}
	return url;
    }

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

	return session.createTopic(topicName);
    }

    protected void closeConnection(Connection con) throws JMSException {
	con.close();
    }

    protected String getClientId(String clientBaseId) {

	return clientBaseId + OPEN_ENCLOSING_BLOCK + this.getNodeLocalAddress()
		+ CLOSE_ENCLOSING_BLOCK;
    }

    protected String getNodeLocalAddress() {

	String localAddress = "localhost";

	if (!StringUtils.isEmpty(this.chainLinkInformation.getLocalBrokerUrl())) {

	    String[] addressSplitByProtocol = this.chainLinkInformation
		    .getLocalBrokerUrl().split("://");
	    String addressWithPort = addressSplitByProtocol[1];
	    localAddress = addressWithPort;
	}

	return localAddress;
    }

    protected String getOutgoingTopicName() {
	return this.chainLinkInformation.getOutgoingTopicName();
    }

    protected String getIncomingTopicName() {
	return this.chainLinkInformation.getIncomingTopicName();
    }
}
