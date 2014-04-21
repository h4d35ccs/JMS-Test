/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain;

import org.apache.commons.lang.StringUtils;
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
    
    
    
    public boolean hasParentNode() {
	boolean hasParent = false;

	if (!StringUtils.isEmpty(this.parentOutgoingTopicUrl)) {

	    hasParent = true;
	}
	return hasParent;
    }
    
    

    @Override
    public boolean isFirstNode() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isLeaf() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isMiddleNode() {
	// TODO Auto-generated method stub
	return false;
    }


    @Override
    public String getParentOutgoingTopicUrl() {
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
	return null;
    }



    
    
    

}
