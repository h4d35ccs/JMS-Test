/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain;

/**
 * @author Otto Abreu
 *
 */
public interface ChainLinkInformation {
    
    boolean hasParentNode();
    
    boolean isFirstNode();
    
    boolean isLeaf();
    
    boolean isMiddleNode();
    
    String getLocalBrokerUrl();
    
    String getOutgoingTopicName();
    
    String getIncomingTopicName();
    
    String getParentOutgoingTopicUrl();

}
