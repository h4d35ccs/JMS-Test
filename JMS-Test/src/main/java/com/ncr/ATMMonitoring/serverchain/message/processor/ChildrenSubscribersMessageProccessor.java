/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.message.processor;

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
public class ChildrenSubscribersMessageProccessor extends ActiveMQMessageProcessor {

    @Autowired
    private ChildrenLinkListHandler childrenLinkHandler;


    protected void processMessage(DataStructure ds) {

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

    @Override
    protected byte getCommandType() {
	// TODO Auto-generated method stub
	return CommandTypes.CONNECTION_INFO;
    }
    
    

}
