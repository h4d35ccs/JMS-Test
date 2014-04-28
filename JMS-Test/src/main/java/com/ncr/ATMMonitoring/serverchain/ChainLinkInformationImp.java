/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Otto Abreu
 * 
 */
@Component
public class ChainLinkInformationImp implements ChainLinkInformation {

    @Value("${jms.parent.outgoing.topic.url:}")
    private String parentOutgoingTopicUrl;

    @Value("${jms.outgoing.topic.name:}")
    private String outgoingTopicName;

    @Value("${jms.localbroker.url:}")
    private String localBrokerUrl;

    @Value("${jms.nodeleaf:}")
    private String nodeLeaf;

    @Value("${jms.incoming.topic.name:}")
    private String incomingTopicName;

    @Value("${jms.broker.url.pattern:}")
    private String brokerUrlPattern;

    private static final String PATTERN_IP_TOKEN = "\\{ip\\}";

    @Autowired
    private ChildrenLinkListHandler childrenLinkListHandler;

    public boolean hasParentNode() {
	boolean hasParent = false;

	if (!StringUtils.isEmpty(this.parentOutgoingTopicUrl)) {

	    hasParent = true;
	}
	return hasParent;
    }

    @Override
    public boolean isFirstNode() {
	boolean isFirstNode = false;
	if (!this.hasParentNode()) {
	    isFirstNode = true;
	}
	return isFirstNode;
    }

    @Override
    public boolean isLeaf() {
	boolean isLeaf = false;

	if (hasParentNode()
		&& (this.childrenLinkListHandler.getChildrenSubscribed()
			.isEmpty())) {
	    isLeaf = true;
	}

	return isLeaf;
    }

    @Override
    public boolean isMiddleNode() {
	boolean isMiddleNode = false;

	if (hasParentNode()
		&& (!this.childrenLinkListHandler.getChildrenSubscribed()
			.isEmpty())) {
	    isMiddleNode = true;
	}
	return isMiddleNode;
    }
    
    @Override
    public boolean isOnlyNode(){
	
	boolean isOnlyNode = false;
	
	if(isFirstNode() && this.childrenLinkListHandler.getChildrenSubscribed()
		.isEmpty()){
	    
	    isOnlyNode = true;
	}
	return isOnlyNode;
    }

    @Override
    public String getParentOutgoingTopicUrl() {
	String parentOutgoingTopicUrl = "";
	
	parentOutgoingTopicUrl = this.generateRemoteBrokerUrl(this.parentOutgoingTopicUrl);
	
	System.out
		.println("parentOutgoingTopicUrl-->" + parentOutgoingTopicUrl);
	
	return parentOutgoingTopicUrl;
    }

    @Override
    public String getOutgoingTopicName() {
	return outgoingTopicName;
    }

    @Override
    public String getLocalBrokerUrl() {
	return localBrokerUrl;
    }

    @Override
    public String getIncomingTopicName() {
	// TODO Auto-generated method stub
	return this.incomingTopicName;
    }

    
    public String generateRemoteBrokerUrl(String ip){
	String completeRemoteUrl ="";
	
	if (!StringUtils.isEmpty(this.brokerUrlPattern)) {

	    completeRemoteUrl = this.brokerUrlPattern;
	    completeRemoteUrl = completeRemoteUrl.replaceFirst(
		    PATTERN_IP_TOKEN, ip);

	} else {

	    completeRemoteUrl = ip;
	}
	return completeRemoteUrl;
    }
}
