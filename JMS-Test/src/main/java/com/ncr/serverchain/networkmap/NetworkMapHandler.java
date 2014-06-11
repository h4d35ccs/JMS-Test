package com.ncr.serverchain.networkmap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

/**
 * Class that generate all the network graph
 * 
 * @author Otto Abreu
 */
@Component
public class NetworkMapHandler {

    private Map<String, NetworkNode> networkNodes = new HashMap<String, NetworkNode>();
    private Map<String, Set<String>> nodesAndChildrens = new HashMap<String, Set<String>>();

    private NetworkMap networkMap;
    private String rootUrlAndPort;


    /**
     * <pre>
     * Update the current information of the network.
     * 
     * From the given list of NodeSpecificInformation, updates or creates the current network
     * 
     * <pre>
     * @param nodesInfos
     */
    public synchronized void updateNetworkMap(
	    List<NodeSpecificInformation> nodesInfos) {

	for (NodeSpecificInformation nodeInfo : nodesInfos) {

	    addIfNotInMap(nodeInfo);

	    if (!nodeInfo.isRoot()) {

		modifyInformationIfInMap(nodeInfo);
		setNodesAndChildren(nodeInfo);
	    }
	}

	
    }

    private void addIfNotInMap(NodeSpecificInformation nodesInfo) {

	if (isNewNode(nodesInfo.getNodeUrlAndPort())) {

	    NetworkNode newNode = createNetworkNode(nodesInfo);

	    this.setRootNodeUrlAndPort(nodesInfo, newNode);

	    this.networkNodes.put(nodesInfo.getNodeUrlAndPort(), newNode);

	}
    }

    private NetworkNode createNetworkNode(NodeSpecificInformation nodesInfo) {
	
	NetworkNode newNode = new NetworkNode();
	newNode.setNodeUrlAndPort(nodesInfo.getNodeUrlAndPort());
	newNode.setRouterTable(nodesInfo.getNodeInfo());
	newNode.setLastcomunicationAt(nodesInfo.getLastCommunication().getTime());
	return newNode;
    }

    private void setRootNodeUrlAndPort(NodeSpecificInformation nodesInfo,
	    NetworkNode newNode) {

	if (nodesInfo.isRoot()) {
	    this.rootUrlAndPort = newNode.getNodeUrlAndPort();
	}
    }

    private boolean isNewNode(String urlAndPort) {

	if (networkNodes.containsKey(urlAndPort)) {

	    return false;

	} else {

	    return true;
	}
    }

    private void modifyInformationIfInMap(NodeSpecificInformation nodesInfo) {

	String nodeUrlAndPort = nodesInfo.getNodeUrlAndPort();

	if (this.networkNodes.containsKey(nodeUrlAndPort)) {

	    NetworkNode updateNode = this.networkNodes.get(nodeUrlAndPort);
	    updateNode.setRouterTable(nodesInfo.getNodeInfo());
	    updateNode.setLastcomunicationAt(new Date().getTime());
	    this.networkNodes.put(nodeUrlAndPort, updateNode);
	}
    }

    private void setNodesAndChildren(NodeSpecificInformation nodesInfo) {

	String parentUrlAndPort = nodesInfo.getParentNodeUrl();
	String childUrlAndPort = nodesInfo.getNodeUrlAndPort();

	if (this.nodesAndChildrens.containsKey(parentUrlAndPort)) {

	    this.deleteOldParentChildRelationshipIfExist(parentUrlAndPort,
		    childUrlAndPort);

	    this.addNewParentChildRelationship(parentUrlAndPort,
		    childUrlAndPort);

	} else {

	    addnewNodeRelationship(parentUrlAndPort, childUrlAndPort);
	}
    }

    private void addNewParentChildRelationship(String parentUrlAndPort,
	    String childUrlAndPort) {
	Set<String> children = this.nodesAndChildrens.get(parentUrlAndPort);
	children.add(childUrlAndPort);

    }

    private void deleteOldParentChildRelationshipIfExist(
	    String parentUrlAndPort, String childUrlAndPort) {

	for (Map.Entry<String, Set<String>> parentChildrenReltaionship : this.nodesAndChildrens
		.entrySet()) {

	    this.deleteParentChildRelationship(childUrlAndPort,
		    parentUrlAndPort, parentChildrenReltaionship);

	}

    }

    private void deleteParentChildRelationship(String childUrlAndPort,
	    String parentUrlAndPort,
	    Map.Entry<String, Set<String>> parentChildrenReltaionship) {

	Set<String> children = parentChildrenReltaionship.getValue();

	for (String actualChildUrl : children) {

	    String actualParentUrl = parentChildrenReltaionship.getKey();

	    if (this.childHasPreviousRelationship(childUrlAndPort,
		    parentUrlAndPort, actualChildUrl, actualParentUrl)) {

		children.remove(childUrlAndPort);

		break;
	    }
	}

    }

    private boolean childHasPreviousRelationship(String childUrlAndPort,
	    String parentUrlAndPort, String actualChildUrl,
	    String actualParentUrl) {

	if (actualChildUrl.equals(childUrlAndPort)
		&& !actualParentUrl.equals(parentUrlAndPort)) {
	    return true;
	} else {
	    return false;
	}
    }

    private void addnewNodeRelationship(String parentUrlAndPort,
	    String childUrlAndPort) {

	Set<String> childrenSet = new HashSet<String>();
	childrenSet.add(childUrlAndPort);
	this.nodesAndChildrens.put(parentUrlAndPort, childrenSet);
    }

    /**
     * Generates the current Network graph using the object NetworkMap
     * 
     * @return NetworkMap
     */
    public NetworkMap getNetworkMap() {

	this.addChildLink();
	NetworkNode rootNode = this.getRootFromMap();

	if (rootNode != null) {

	    rootNode.setLastcomunicationAt(NetworkNode.ROOT_COMUNICATION_DEFAULT_VALUE);
	}
	this.networkMap = new NetworkMap(rootNode);

	return networkMap;
    }

    private void addChildLink() {

	for (Map.Entry<String, Set<String>> nodeAndChild : this.nodesAndChildrens
		.entrySet()) {

	    Set<String> childrens = nodeAndChild.getValue();
	    String parentNodeUrl = nodeAndChild.getKey();
	    List<NetworkNode> childrenNodes = this
		    .getChildrenNodesList(childrens);
	    this.setChildrenInParent(parentNodeUrl, childrenNodes);

	}

    }

    private List<NetworkNode> getChildrenNodesList(Set<String> childrens) {

	List<NetworkNode> childrenNodes = new ArrayList<NetworkNode>();

	for (String child : childrens) {

	    NetworkNode childNode = this.networkNodes.get(child);
	    childrenNodes.add(childNode);
	}
	return childrenNodes;
    }

    private void setChildrenInParent(String parentNodeUrl,
	    List<NetworkNode> childrenNodes) {

	NetworkNode parentNetworkNode = this.networkNodes.get(parentNodeUrl);
	parentNetworkNode.setChildren(childrenNodes);
    }

    private NetworkNode getRootFromMap() {
	NetworkNode rootNode = this.networkNodes.get(this.rootUrlAndPort);
	return rootNode;
    }
    
    /**
     * Return a network map with only a root node with the given information
     * @param rootUrlAndPort
     * @return NetworkMap
     */
    public NetworkMap constructEmptyNetworkMap(String rootUrlAndPort) {
	NetworkNode soleRootNode = new NetworkNode();
	soleRootNode.setLastcomunicationAt(NetworkNode.ROOT_COMUNICATION_DEFAULT_VALUE);
	soleRootNode.setNodeUrlAndPort(rootUrlAndPort);
	this.networkMap = new NetworkMap(soleRootNode);
	return networkMap;
    }
}
