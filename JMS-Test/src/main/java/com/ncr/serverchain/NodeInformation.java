/**
 * 
 */
package com.ncr.serverchain;

/**
 * Returns the information of the node based on the configuration file
 * 
 * @author Otto Abreu
 * 
 */
public interface NodeInformation {

    /**
     * True means that this node has a parent node associated
     * 
     * @return boolean
     */
    boolean hasParentNode();

    /**
     * Indicate the current position of the node.
     * 
     * @see NodePosition
     * @return NodePosition
     */
    NodePosition getNodePosition();

    /**
     * Return the local broker URL configured in the property file
     * 
     * @return String
     */
    String getLocalBrokerUrl();

    /**
     * Returns the outgoing topic name configured in the property file
     * 
     * @return String
     */
    String getOutgoingTopicName();

    /**
     * Returns the incoming topic name configured in the property file
     * 
     * @return
     */
    String getIncomingTopicName();

    /**
     * Gets the template from the property and puts the parent ip to generate a
     * valid remote url
     * 
     * @return
     */
    String getParentOutgoingTopicUrl();

    /**
     * Gets the template from the property and puts the given ip to generate a
     * valid JMS url
     * 
     * @param ip
     *            String
     * @return String
     */
    String generateRemoteBrokerUrl(String ip);

    /**
     * Returns the local URL without the protocol
     * 
     * @return String
     */
    String getLocalUrl();

    /**
     * Returns the parent url without protocol
     * 
     * @return
     */
    String getParentUrl();
    
    /**
     * Return true if this node is a leaf in the network
     * @return boolean
     */
    boolean isLeaf();
    /**
     * Return true if the node is the fist one and is not the only one
     * @return boolean
     */
    boolean isRoot();
   /**
    * Return true if the node is not part of a network
    * @return
    */
    boolean isOnlyNode();
    /**
     * return true if the node is a middle node
     * @return
     */
    boolean isMiddleNode();

}
