/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.topicactor.TopicActor;

/**
 * @author Otto Abreu
 *
 */
@Component
public class ChildrenLinkListHandler {
    
    private Set<String> childrenSubscribed = new HashSet<String>();
    
    
    public boolean isChildSubscribed(String childIpandPort){
	
	boolean childSubscribed = false;
	
	if(this.childrenSubscribed.contains(childIpandPort)){
	    childSubscribed = true;
	}
	
	return childSubscribed;
    }
    
    
    public void addChildIpToSubscribedList(String childIpandPort){
	System.out.println("added-->"+childIpandPort);
	this.childrenSubscribed.add(childIpandPort);
	System.out.println("set-->"+childrenSubscribed);
    }
    
    public void removeChildIpToSubscribedList(String childIpandPort){
	this.childrenSubscribed.remove(childIpandPort);
    }
    
    public static boolean isChildSubscription(String completeChildId){
	return completeChildId.startsWith(TopicActor.SUBSCRIBER_BASE_ID);
    }
    
    public static String getIpAndPortFromConnectionId(String completeChildId){
	
	String childAddressAndPort ="";
	int openBraketPosition = completeChildId.indexOf(TopicActor.OPEN_ENCLOSING_BLOCK);
	int closeBraketPosition = completeChildId.indexOf(TopicActor.CLOSE_ENCLOSING_BLOCK);
	if(openBraketPosition > 0 && closeBraketPosition > 0 ){
	    childAddressAndPort = completeChildId.substring(openBraketPosition+1, closeBraketPosition);
	}
	
	return childAddressAndPort;
    }
    
    
}
