package com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ncr.ATMMonitoring.routertable.RouterTableHandler;
import com.ncr.ATMMonitoring.serverchain.message.specific.incoming.UpdateMultipleRouterTable;
import com.ncr.ATMMonitoring.serverchain.message.specific.outgoing.ChangeLeafNodeRouterTableRequest;
import com.ncr.serverchain.message.specific.DirectCommunicationMessage;
import com.ncr.serverchain.message.specific.UpdateRouterTableMessage.UpdateType;
import com.ncr.serverchain.message.specific.strategy.BroadcastType;
import com.ncr.serverchain.message.specific.strategy.imp.DirectNodeCommunication;
import com.ncr.serverchain.message.wrapper.IncomingMessage;
import com.ncr.serverchain.message.wrapper.MessageWrapper;

/**
 * <pre>
 * Strategy used to change the router table of an Leaf node.
 * 
 * After the node is updated, the message change from ChangeLeafNodeRouterTable to UpdateMultipleRouterTable
 * Because the change made have to propagate
 * </pre>
 * @author Otto Abreu
 * 
 */
public class ChangeLeafNodeRouterTableStrategy extends DirectNodeCommunication {

   
    private static final Logger logger = Logger
	    .getLogger(UpdateMultipleRouterTableStrategy.class);
    
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#canProcessSpecificMessage()
     */
    @Override
    public boolean canProcessSpecificMessage() {
	// Always true to allow
	//message propagation
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
	logger.debug("leaf? "+this.isLeaf());
	if (this.isLeaf() && this.messageWasAddressedToThisNode()) {

	    Properties newTable = this.getNewTable();
	    logger.debug("adding new Routing table to leaf: "+newTable);
	    RouterTableHandler.clearAndUpdateAllTable(newTable);
	    logger.debug("Table after update: "+RouterTableHandler.tableTotring());
	}
    }

    private Properties getNewTable() {
	Properties newTable = ((ChangeLeafNodeRouterTableRequest) this
		.getDirectNodeComunication()).getNewRouterTable();
	return newTable;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy#broadcastDirection()
     */
    @Override
    public BroadcastType broadcastDirection() {

	BroadcastType broadcast = BroadcastType.NONE;

	if (this.isLeaf()  && this.messageWasAddressedToThisNode()) {

	    broadcast = BroadcastType.TURN_BACK;

	}else if(this.isMiddle()){
	    
	    broadcast = BroadcastType.ONE_WAY;
	}

	return broadcast;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp.
     * DirectNodeCommunication#getDirectNodeComunication()
     */
    @Override
    protected DirectCommunicationMessage getDirectNodeComunication() {
	DirectCommunicationMessage directComunicationMessage = (DirectCommunicationMessage) this.messageToProcess;
	return directComunicationMessage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp.BaseStrategy
     * #getTurnBackMessage()
     */
    @Override
    public MessageWrapper getTurnBackMessage() {
	Properties newTable = this.getNewTable();
	
	UpdateMultipleRouterTable multipleUpdate = new UpdateMultipleRouterTable(
		newTable);
	
	multipleUpdate.setUpdateType(UpdateType.UPDATE);
	IncomingMessage turnbackMessage = new IncomingMessage(
		"Multiple table Update",  new Date().getTime());
	turnbackMessage.setSpecificMessage(multipleUpdate);
	
	return turnbackMessage;
    }

 
}
