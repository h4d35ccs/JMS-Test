/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.topicactor.TopicActor;

/**
 * @author Otto Abreu
 * 
 */
@Component
public class ChildrenLinkListHandler {

    public Set<String> getChildrenSubscribed() {
        return childrenSubscribed;
    }

    private static final Logger logger = Logger
	    .getLogger(ChildrenLinkListHandler.class);

    private Set<String> childrenSubscribed = new HashSet<String>();

    private static final String SUBSCRIPTION_LIST_FILENAME = "subscription_list.bin";

    public boolean isChildSubscribed(String childIpandPort) {

	boolean childSubscribed = false;

	if (this.childrenSubscribed.contains(childIpandPort)) {
	    childSubscribed = true;
	}
	logger.debug("is "+childIpandPort+" subscribed? "+childSubscribed);
	return childSubscribed;
    }

    public void addChildIpToSubscribedList(String childIpandPort) {
	
	this.childrenSubscribed.add(childIpandPort);
	
    }

    
   

    public void removeChildIpToSubscribedList(String childIpandPort) {
	this.childrenSubscribed.remove(childIpandPort);
    }

    public static boolean isChildSubscription(String completeChildId) {
	return completeChildId.startsWith(TopicActor.SUBSCRIBER_BASE_ID);
    }

    public static String getIpAndPortFromConnectionId(String completeChildId) {

	String childAddressAndPort = "";
	int openBraketPosition = completeChildId
		.indexOf(TopicActor.OPEN_ENCLOSING_BLOCK);
	int closeBraketPosition = completeChildId
		.indexOf(TopicActor.CLOSE_ENCLOSING_BLOCK);
	if (openBraketPosition > 0 && closeBraketPosition > 0) {
	    childAddressAndPort = completeChildId.substring(
		    openBraketPosition + 1, closeBraketPosition);
	}

	return childAddressAndPort;
    }
    
}
