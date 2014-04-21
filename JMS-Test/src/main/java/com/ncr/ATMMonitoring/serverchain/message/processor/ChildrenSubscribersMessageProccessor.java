/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.message.processor;

import javax.jms.Message;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.CommandTypes;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.DataStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.ChildrenLinkListHandler;

/**
 * @author Otto Abreu
 * 
 */
@Component("childrenSubscribersMessageProccessor")
public class ChildrenSubscribersMessageProccessor implements MessageProcessor {

    @Autowired
    private ChildrenLinkListHandler childrenLinkHandler;

    @Override
    public void processReceivedMessage(Message message) {

	if (message instanceof ActiveMQMessage) {

	    ActiveMQMessage msg = (ActiveMQMessage) message;
	    DataStructure ds = msg.getDataStructure();

	    if (ds != null
		    && (ds.getDataStructureType() == CommandTypes.CONNECTION_INFO)) {

		this.processMessage(ds);
	    }
	}else{
	    throw new IllegalArgumentException("the message class should be ActiveMQMessage, received: "
				+ message.getClass());
	    
	}

    }

    private void processMessage(DataStructure ds) {

	String childId = this.getChildId(ds);
	
	if (ChildrenLinkListHandler.isChildSubscription(childId)) {
	   
	    String childUrlAndPort = this.getChildUrlAndPort(childId);
	    this.addChildUrlAndPortToList(childUrlAndPort);
	}
    }

    private String getChildId(DataStructure ds) {

	ConnectionInfo connectionInfo = (ConnectionInfo) ds;
	String childId = connectionInfo.getClientId();
	return childId;
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

}
