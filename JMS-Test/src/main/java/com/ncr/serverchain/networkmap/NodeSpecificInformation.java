package com.ncr.serverchain.networkmap;

import java.io.Serializable;
import java.util.Date;

/**
 * Object that holds the information of a node 
 * @author Otto Abreu
 * 
 */
public class NodeSpecificInformation implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String nodeUrlAndPort;
    private String nodeInfo;
    private String parentNodeUrl;
    private boolean isRoot;
    private Date lastCommunication;

    public NodeSpecificInformation(String nodeUrlAndPort, String nodeInfo,
	    String parentUrl) {
	
	super();
	this.nodeUrlAndPort = nodeUrlAndPort;
	this.nodeInfo = nodeInfo;
	this.parentNodeUrl = parentUrl;
	this.lastCommunication = new Date();
    }

    public String getNodeUrlAndPort() {
	return nodeUrlAndPort;
    }

    public String getNodeInfo() {
	return nodeInfo;
    }
    
    public void setNodeAsRoot(){
	this.isRoot = true;
    }
    
    public boolean isRoot(){
	return this.isRoot;
    }

    public String getParentNodeUrl() {
        return parentNodeUrl;
    }

    public Date getLastCommunication() {
        return lastCommunication;
    }

}
