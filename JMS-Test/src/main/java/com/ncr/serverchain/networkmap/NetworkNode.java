package com.ncr.serverchain.networkmap;

import java.util.Date;
import java.util.List;

/**
 * Represent a node in the network
 * Holds the information of an individual node
 * @author Otto Abreu
 * 
 */
public class NetworkNode {

    private String nodeUrlAndPort;
    private String nodeInfo;
    private long lastcomunicationInMilisecAt;
    private List<NetworkNode> children;
    
    public static final long ROOT_COMUNICATION_DEFAULT_VALUE = 0;

   

    public String getNodeUrlAndPort() {
	return nodeUrlAndPort;
    }

    public void setNodeUrlAndPort(String nodeUrlAndPort) {
	this.nodeUrlAndPort = nodeUrlAndPort;
    }

    public String getNodeInfo() {
	return nodeInfo;
    }

    public void setRouterTable(String routerTable) {
	this.nodeInfo = routerTable;
    }

    public List<NetworkNode> getChildren() {
	return children;
    }

    public void setChildren(List<NetworkNode> children) {
	this.children = children;
    }

    public long getLastcomunicationAt() {
	return lastcomunicationInMilisecAt;
    }

    public void setLastcomunicationAt(long lastcomunicationAt) {
	this.lastcomunicationInMilisecAt = lastcomunicationAt;
    }

    @Override
    public String toString() {

	String children = "IS LEAF";

	if (this.children != null && !this.children.isEmpty()) {

	    children = " children= {" + this.children.toString() + "}";
	}

	return " [node =" + nodeUrlAndPort + " last comunication in second:"
		+ this.getSecondsFromLastComunication() + ", routerTable="
		+ nodeInfo + ", " + children + "]";
    }

    /**
     * Returns the last time (in milisec) when information regarding this node was received
     * @return long
     */
    public long getSecondsFromLastComunication() {
	long secondsFromLastComunication = 0;
	
	if (this.lastcomunicationInMilisecAt > 0){
	 
	   long now = new Date().getTime();
	   long lastCom = new Date(this.lastcomunicationInMilisecAt).getTime();
	   secondsFromLastComunication = ((now - lastCom)/1000) ;
	}
	return secondsFromLastComunication;
    }

}
