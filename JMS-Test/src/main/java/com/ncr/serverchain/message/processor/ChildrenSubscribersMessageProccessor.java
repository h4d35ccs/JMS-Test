package com.ncr.serverchain.message.processor;

import org.apache.activemq.command.CommandTypes;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.DataStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.serverchain.ChildrenLinkListHandler;

/**
 * Class that extract from the Connection information the URL of the subscribed
 * children
 * 
 * @author Otto Abreu
 * 
 */
@Component("childrenSubscribersMessageProccessor")
public class ChildrenSubscribersMessageProccessor extends
	ActiveMQMessageProcessor {

    @Autowired
    private ChildrenLinkListHandler childrenLinkHandler;

    protected void processMessage(DataStructure ds) {

	String subscriberId = this.getSubscriberId(ds);

	this.registerOnlyChildSubscriptions(subscriberId);
    }

    private String getSubscriberId(DataStructure ds) {

	ConnectionInfo connectionInfo = (ConnectionInfo) ds;
	String subscriberId = connectionInfo.getClientId();
	return subscriberId;
    }

    private void registerOnlyChildSubscriptions(String childId) {

	if (ChildrenLinkListHandler.isChildSubscription(childId)) {

	    String childUrlAndPort = this.getChildUrlAndPort(childId);
	    this.addChildUrlAndPortToList(childUrlAndPort);
	}
    }

    private String getChildUrlAndPort(String childId) {

	String childUrlAndPort = ChildrenLinkListHandler
		.getIpAndPortFromConnectionId(childId);
	return childUrlAndPort;
    }

    private void addChildUrlAndPortToList(String childUrlAndPort) {

	if (!this.childrenLinkHandler.isChildSubscribed(childUrlAndPort)) {
	    this.childrenLinkHandler
		    .addChildIpToSubscribedList(childUrlAndPort);
	}
    }

    @Override
    protected byte getCommandType() {
	
	return CommandTypes.CONNECTION_INFO;
    }

}
