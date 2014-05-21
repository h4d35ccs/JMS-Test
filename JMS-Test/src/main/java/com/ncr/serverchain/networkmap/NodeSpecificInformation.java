package com.ncr.serverchain.networkmap;

import java.io.Serializable;

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
    private String routerTable;
    private String parentNodeUrl;
    private boolean isRoot;

    public NodeSpecificInformation(String nodeUrlAndPort, String routerTable,
	    String parentUrl) {
	super();
	this.nodeUrlAndPort = nodeUrlAndPort;
	this.routerTable = routerTable;
	this.parentNodeUrl = parentUrl;
    }

    public String getNodeUrlAndPort() {
	return nodeUrlAndPort;
    }

    public String getRouterTable() {
	return routerTable;
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
    
    

}
