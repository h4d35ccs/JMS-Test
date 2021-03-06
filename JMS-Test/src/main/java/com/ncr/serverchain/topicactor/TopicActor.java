package com.ncr.serverchain.topicactor;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ConnectionClosedException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.serverchain.NodeInformation;
/**
 * Class that hold de basic logic for interact ( consume or produce messages) with a topic
 * @author Otto Abreu
 *
 */
@Component
public abstract class TopicActor {

    @Autowired
    private NodeInformation nodeInformation;

    public static final String SUBSCRIBER_BASE_ID = "subscriber";

    protected static final String LOCAL_MONITOR_SUBSCRIBER_BASE_ID = "localhostMonitor";

    protected static final String PARENT_SUBSCRIBER_BASE_ID = "parentsubscriber";

    public static final String OPEN_ENCLOSING_BLOCK = "[";

    public static final String CLOSE_ENCLOSING_BLOCK = "]";

    protected static final int USE_LOCAL_BROKER_URL = 0;

    protected static final int USE_PARENT_OUTGOING_BROKER_URL = 1;

    protected static final int USE_CHILD_INCOMING_BROKER_URL = 2;

    protected static final int USE_EXTERNAL_BROKER_URL = 3;

    protected String externalBrokerUrl;

    private static final Logger logger = Logger
	    .getLogger(TopicActor.class);
    
    
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
	    url = this.nodeInformation.getLocalBrokerUrl();
	    break;

	case USE_PARENT_OUTGOING_BROKER_URL:
	    url = this.nodeInformation.getParentOutgoingTopicUrl();
	    break;

	case USE_EXTERNAL_BROKER_URL:

	    this.validateNotNullExternalBrokerUrl();

	    url = this.externalBrokerUrl;

	    break;

	default:
	    throw new IllegalArgumentException(
		    "Unknown broker type, use TopicActor constants for defining the broker type, Received: "
			    + brokerType);
	}
	return url;
    }

    private void validateNotNullExternalBrokerUrl() {
	if (this.externalBrokerUrl == null) {
	    throw new IllegalArgumentException(
		    "The external broker URL was not set, please use the setter method: public void setExternalBrokerUrl(String externalBrokerUrl) ");
	}
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
	
	try {
	    con.close();
	} catch (ConnectionClosedException e) {
	    logger.warn("can not close the connection because was already closed");
	}
    }

    protected String getClientId(String clientBaseId) {

	return clientBaseId + OPEN_ENCLOSING_BLOCK + this.getNodeLocalAddress()
		+ CLOSE_ENCLOSING_BLOCK;
    }

    protected String getNodeLocalAddress() {

	String localAddress = "localhost";

	if (!StringUtils.isEmpty(this.nodeInformation.getLocalBrokerUrl())) {

	    String[] addressSplitByProtocol = this.nodeInformation
		    .getLocalBrokerUrl().split("://");
	    String addressWithPort = addressSplitByProtocol[1];
	    localAddress = addressWithPort;
	}

	return localAddress;
    }

    protected String getOutgoingTopicName() {
	return this.nodeInformation.getOutgoingTopicName();
    }

    protected String getIncomingTopicName() {
	return this.nodeInformation.getIncomingTopicName();
    }
    
    protected void setExternalBrokerUrl(String externalBrokerUrl) {
        this.externalBrokerUrl = externalBrokerUrl;
    }

}
