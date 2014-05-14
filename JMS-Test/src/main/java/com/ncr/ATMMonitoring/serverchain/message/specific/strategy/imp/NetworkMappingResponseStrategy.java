package com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp;

import org.apache.log4j.Logger;

import com.ncr.ATMMonitoring.routertable.RouterTableHandler;
import com.ncr.ATMMonitoring.serverchain.message.specific.incoming.NetworkMappingResponse;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.BroadcastType;
import com.ncr.ATMMonitoring.serverchain.networkmap.NetworkMapHandler;

/**
 * <pre>
 * Strategy that process a NetworkMappingResponse message
 * 
 * This strategy starts in the next node after the request reaches a leaf node. (Starts in the parent of a leaf).
 * In each node, collects the information regarding the node an broadcast the message (BroadcastType.ONE_WAY)
 * to the parent until it reaches the root 
 * 
 * 
 * @author Otto Abreu
 * <pre>
 * 
 */
public class NetworkMappingResponseStrategy extends BaseStrategy {

    private static final Logger logger = Logger
	    .getLogger(NetworkMappingResponseStrategy.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy#canProcessSpecificMessage()
     */
    @Override
    public boolean canProcessSpecificMessage() {

	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy#processSpecificMessage()
     */
    @Override
    public void processSpecificMessage() {

	if (this.isRoot()) {
	    processInRoot();
	} else {
	    processNodeMiddle();
	}

    }

    private void processInRoot() {
	processNodeInformationRoot();
	processFinalMapping();

    }

    private void processNodeInformationRoot() {

	castMessageToNetworkMappingResponse().addNodeInformationRoot(
		this.nodeInformation.getLocalUrl());
	logger.debug("Root Node Information Added");
    }

    private void processFinalMapping() {
	NetworkMapHandler mapHandler = (NetworkMapHandler) this
		.getSpringBean(NetworkMapHandler.class);
	NetworkMappingResponse responseMessage = castMessageToNetworkMappingResponse();
	mapHandler.updateNetworkMap(responseMessage.getNodesRecolectedInfo());
    }

    private void processNodeMiddle() {

	String nodeUrlAndPort = this.nodeInformation.getLocalUrl();
	String routerTable = RouterTableHandler.tableTotring();
	String parentUrl = this.nodeInformation.getParentUrl();

	castMessageToNetworkMappingResponse().addNodeInformationLeaforMiddle(
		nodeUrlAndPort, routerTable, parentUrl);

	logger.debug("middle Node Information Added");
    }

    private NetworkMappingResponse castMessageToNetworkMappingResponse() {
	return (NetworkMappingResponse) this.messageToProcess;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy#broadcastDirection()
     */
    @Override
    public BroadcastType broadcastDirection() {

	BroadcastType broadcast = BroadcastType.ONE_WAY;

	if (this.isRoot()) {
	    broadcast = BroadcastType.NONE;
	}
	return broadcast;
    }

}
