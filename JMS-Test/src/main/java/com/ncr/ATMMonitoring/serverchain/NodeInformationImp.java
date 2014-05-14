/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Concrete class of NodeInformation.
 * 
 * Returns the information regarding the node, such as the position, the topics
 * names, the brokers URL
 * 
 * @author Otto Abreu
 * 
 * </pre>
 * 
 */
@Component
public class NodeInformationImp implements NodeInformation {

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
    
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.ChainLinkInformation#getNodePosition()
     */
    @Override
    public NodePosition getNodePosition() {
	NodePosition nodePosition = null;

	if (this.isFirstNode()) {

	    nodePosition = NodePosition.FIRST_NODE;

	} else if (this.isLeaf()) {

	    nodePosition = NodePosition.LEAF_NODE;

	} else if (this.isMiddleNode()) {

	    nodePosition = NodePosition.MIDDLE_NODE;

	} else if (this.isOnlyNode()) {

	    nodePosition = NodePosition.ONLY_NODE;
	}

	return nodePosition;
    }

    private boolean isFirstNode() {
	boolean isFirstNode = false;
	
	if (!this.hasParentNode()) {
	    isFirstNode = true;
	}
	return isFirstNode;
    }

    private boolean isLeaf() {
	boolean isLeaf = false;

	if (hasParentNode()
		&& (this.childrenLinkListHandler.getChildrenSubscribed()
			.isEmpty())) {
	    isLeaf = true;
	}

	return isLeaf;
    }

    private boolean isMiddleNode() {
	
	boolean isMiddleNode = false;

	if (hasParentNode()
		&& (!this.childrenLinkListHandler.getChildrenSubscribed()
			.isEmpty())) {
	    isMiddleNode = true;
	}
	return isMiddleNode;
    }

    private boolean isOnlyNode() {

	boolean isOnlyNode = false;

	if (isFirstNode()
		&& this.childrenLinkListHandler.getChildrenSubscribed()
			.isEmpty()) {

	    isOnlyNode = true;
	}
	return isOnlyNode;
    }
    
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.ChainLinkInformation#hasParentNode()
     */
    @Override
    public boolean hasParentNode() {
	boolean hasParent = false;

	if (!StringUtils.isEmpty(this.parentOutgoingTopicUrl)) {

	    hasParent = true;
	}
	return hasParent;
    }
    
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.ChainLinkInformation#getParentOutgoingTopicUrl()
     */
    @Override
    public String getParentOutgoingTopicUrl() {
	String parentOutgoingTopicUrl = "";

	parentOutgoingTopicUrl = this
		.generateRemoteBrokerUrl(this.parentOutgoingTopicUrl);

	return parentOutgoingTopicUrl;
    }
    
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.ChainLinkInformation#getOutgoingTopicName()
     */
    @Override
    public String getOutgoingTopicName() {
	return outgoingTopicName;
    }
    
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.ChainLinkInformation#getLocalBrokerUrl()
     */
    @Override
    public String getLocalBrokerUrl() {
	return localBrokerUrl;
    }
    
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.ChainLinkInformation#getIncomingTopicName()
     */
    @Override
    public String getIncomingTopicName() {

	return this.incomingTopicName;
    }
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.ChainLinkInformation#generateRemoteBrokerUrl(java.lang.String)
     */
    @Override
    public String generateRemoteBrokerUrl(String ip) {
	String completeRemoteUrl = "";

	if (!StringUtils.isEmpty(this.brokerUrlPattern)) {

	    completeRemoteUrl = this.brokerUrlPattern;
	    completeRemoteUrl = completeRemoteUrl.replaceFirst(
		    PATTERN_IP_TOKEN, ip);

	} else {

	    completeRemoteUrl = ip;
	}
	return completeRemoteUrl;
    }

    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.NodeInformation#getLocalUrl()
     */
    @Override
    public String getLocalUrl() {
	String localUrl ="";
	if(StringUtils.isNotEmpty(this.localBrokerUrl)){
	    String[] protocolAndUrl = this.localBrokerUrl.split("://");
	    localUrl = protocolAndUrl[1];
	}
	return localUrl;
		
    }
    
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.NodeInformation#getParentUrl()
     */
    @Override
    public String getParentUrl(){
	return this.parentOutgoingTopicUrl;
    }

}
