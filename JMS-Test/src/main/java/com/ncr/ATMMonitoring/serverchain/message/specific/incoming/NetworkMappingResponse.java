package com.ncr.ATMMonitoring.serverchain.message.specific.incoming;

import java.util.ArrayList;
import java.util.List;

import com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.StrategyMapper;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp.NetworkMappingResponseStrategy;
import com.ncr.ATMMonitoring.serverchain.networkmap.NodeSpecificInformation;

/**
 * Holds the information of all visited nodes when a network mapping is required
 * @author Otto Abreu
 * 
 */
@StrategyMapper(strategyMapping = NetworkMappingResponseStrategy.class)
public class NetworkMappingResponse implements SpecificMessage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<NodeSpecificInformation> nodesRecolectedInfo;

    public NetworkMappingResponse() {
	super();
	this.nodesRecolectedInfo = new ArrayList<NodeSpecificInformation>();
    }

    /**
     * Adds the information regarding to the leaf or middle nodes
     * @param nodeUrlAndPort String ip/url and port of the  current node (defined in the property file)
     * @param routerTable String The string representation of the router table
     * @param parentUrl String String ip/url and port of the parent node (defined in the property file)
     */
    public void addNodeInformationLeaforMiddle(String nodeUrlAndPort,
	    String routerTable, String parentUrl) {

	NodeSpecificInformation nodeSpecificInfo = new NodeSpecificInformation(
		nodeUrlAndPort, routerTable, parentUrl);
	this.nodesRecolectedInfo.add(nodeSpecificInfo);
    }
/**
 *  Adds the information regarding to the  root node
 * @param nodeUrlAndPort String ip/url and port of the  current node (defined in the property file)
 */
    public void addNodeInformationRoot(String nodeUrlAndPort) {
	NodeSpecificInformation nodeSpecificInfo = new NodeSpecificInformation(
		nodeUrlAndPort, null, null);
	nodeSpecificInfo.setNodeAsRoot();
	this.nodesRecolectedInfo.add(nodeSpecificInfo);
    }

    /**
     * Returns the list containing the info(NodeSpecificInformation) of each node 
     * @return
     */
    public List<NodeSpecificInformation> getNodesRecolectedInfo() {
	return nodesRecolectedInfo;
    }

}
