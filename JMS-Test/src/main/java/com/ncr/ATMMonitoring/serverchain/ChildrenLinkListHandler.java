/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.topicactor.TopicActor;

/**
 * <pre>
 * Class in charge of handling the child node subscription list.
 * 
 * This class provides methods to add or remove subscribers from the list,
 *  and also to know if the given subscriber is register in the list .
 *  
 *  
 *  This is only a referential list, created after a child node subscribes to the parent,
 *  and do not operates over JMS
 * 
 * @author Otto Abreu
 * 
 * </pre>
 */
@Component
public class ChildrenLinkListHandler {

    private static final Logger logger = Logger
	    .getLogger(ChildrenLinkListHandler.class);

    private Set<String> childrenSubscribed = new HashSet<String>();

    /**
     * verify if the given ip is already in the subscription list
     * This does not ask to the current subscription status handle by JMS
     * @param childIpandPort
     * @return boolean
     */
    public boolean isChildSubscribed(String childIpandPort) {

	boolean childSubscribed = false;

	if (this.childrenSubscribed.contains(childIpandPort)) {
	    childSubscribed = true;
	}

	logger.debug("is " + childIpandPort + " subscribed? " + childSubscribed);

	return childSubscribed;
    }
    
    /**
     * Returns a copy of the current list of subscribed childrens
     * @return Set<String>
     */
    public Set<String> getChildrenSubscribed() {
	return new HashSet<String>(childrenSubscribed);
    }

    /**
     * Adds a child ip to the list of subscriptions
     * This does not affect the current subscription status handle by JMS
     * @param childIpandPort
     */
    public void addChildIpToSubscribedList(String childIpandPort) {

	this.childrenSubscribed.add(childIpandPort);

    }
    /**
     * Removes a child ip from the list of subscriptions.
     * This does not affect the current subscription status handle by JMS
     * @param childIpandPort
     */
    public void removeChildIpToSubscribedList(String childIpandPort) {
	this.childrenSubscribed.remove(childIpandPort);
    }

    /**
     * <pre>
     * Verify if the given connection ID belongs to a subscriber.
     * 
     * the connection id have the following pattern: subscriber xxx.xxx.xxx:xxx
     * 
     * <pre>
     * @param completeChildId
     * @return
     */
    public static boolean isChildSubscription(String completeChildId) {
	return completeChildId.startsWith(TopicActor.SUBSCRIBER_BASE_ID);
    }

    /**
     * Returns only ip and port from the connectionID
     * @param completeChildId
     * @return String
     */
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
