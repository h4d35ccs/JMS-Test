package com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp;

import java.util.Date;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ncr.ATMMonitoring.routertable.RouterTableHandler;
import com.ncr.ATMMonitoring.serverchain.message.specific.incoming.UpdateMultipleRouterTable;
import com.ncr.serverchain.message.specific.SpecificMessage;
import com.ncr.serverchain.message.specific.UpdateRouterTableMessage.UpdateType;
import com.ncr.serverchain.message.specific.strategy.BroadcastType;
import com.ncr.serverchain.message.specific.strategy.imp.BaseStrategy;
import com.ncr.serverchain.message.wrapper.MessageWrapper;
import com.ncr.serverchain.message.wrapper.OutgoingMessage;

/**
 * <pre>
 * Strategy to perform multiple Router table values in the nodes.
 * 
 * This strategy is fired after an direct update is performed in a leaf node
 * for that reason this strategy starts in a middle node always.
 * 
 * The strategy can process as long as the current node does not have the new values presents 
 * ( if so is assumed that the node was updated in a previous run).
 * 
 * The propagation algorithm is similar to the used in UpdateRouterTableStrategy, 
 * where first an update is performed in the same branch and only when the root is reached, 
 * the message changes to REMOVE, and because the branch is updated when the message propagate from the root
 * it will not enter because it will not meet with the canProcessSpecificMessage ( will return false).
 * 
 * In the middle nodes will add/update all the values if is an update, otherwise remove
 * 
 * In the leaf only will remove if the table have some values in common, 
 * this means that the node was the previous processing node.
 * 
 * In the root will change the Update for Remove
 * 
 * 
 * </pre>
 * 
 * @author Otto Abreu
 * 
 */
public class UpdateMultipleRouterTableStrategy extends BaseStrategy {

    private static final Logger logger = Logger
	    .getLogger(UpdateMultipleRouterTableStrategy.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy#canProcessSpecificMessage()
     */
    @Override
    public boolean canProcessSpecificMessage() {

	boolean canProcess = false;
	logger.debug("routerTableIsDiferent?"+routerTableIsDiferent()+" - routerTableDoNotContainsValues? "+routerTableDoNotContainsValues());
	if (routerTableIsDiferent() && routerTableDoNotContainsValues()) {
	    
	    canProcess = true;

	} else if (this.isRoot()) {

	    canProcess = true;
	}

	return canProcess;
    }

    private boolean isUpdate() {

	if (getUpdateTypeFromMessage().equals(UpdateType.UPDATE)) {

	    return true;

	} else {

	    return false;
	}

    }

    private boolean routerTableIsDiferent() {

	Properties tableToCompare = getUpdateMultipleRouterTableMessage()
		.getNewValues();

	if (!RouterTableHandler.isEqualTo(tableToCompare)) {

	    return true;
	} else {
	    return false;
	}
    }

    private boolean routerTableDoNotContainsValues() {
	Properties tableToCompare = getUpdateMultipleRouterTableMessage()
		.getNewValues();

	if (!RouterTableHandler.allValuesPresentInTable(tableToCompare)) {

	    return true;

	} else {

	    return false;
	}
    }

    private boolean isRemove() {

	if (getUpdateTypeFromMessage().equals(UpdateType.REMOVE_ONLY)) {

	    return true;

	} else {

	    return false;
	}

    }

    private boolean matriculasPresentInTable() {
	Properties tableToCompare = getUpdateMultipleRouterTableMessage()
		.getNewValues();
	Set<String> commonValues = RouterTableHandler
		.commonPresentMatricula(tableToCompare);

	if (!commonValues.isEmpty()) {
	    return true;

	} else {

	    return false;
	}
    }

    private UpdateMultipleRouterTable getUpdateMultipleRouterTableMessage() {

	return (UpdateMultipleRouterTable) this.messageToProcess;
    }

    private UpdateType getUpdateTypeFromMessage() {
	UpdateType updateType = this.getUpdateMultipleRouterTableMessage()
		.getUpdateType();
	return updateType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy#processSpecificMessage()
     */
    @Override
    public void processSpecificMessage() {
	logger.debug("router table before update: "+RouterTableHandler.tableTotring());
	if (this.isMiddle()) {

	    processOnMiddleNodes();

	} else if (this.isLeaf()) {

	    processOnLeaf();

	} 
	logger.debug("router table after update: "+RouterTableHandler.tableTotring());
    }

    private void processOnMiddleNodes() {

	Properties newValues = this.getUpdateMultipleRouterTableMessage()
		.getNewValues();
	logger.debug("router table multiple values update middle node");

	if (this.isUpdate()) {

	    logger.debug("is going to add all the values: " + newValues);
	    addAllNewValues(newValues);

	} else if (this.isRemove() && this.matriculasPresentInTable()) {
	    logger.debug("is going to remove all the values: " + newValues);
	    removePresentMatriculas(newValues);
	}
    }

    private void addAllNewValues(Properties newValues) {

	Set<?> matriculas = newValues.keySet();
	addValuesToTable(matriculas, newValues);
    }

    private void addValuesToTable(Set<?> matriculas, Properties newValues) {

	for (Object matricula : matriculas) {

	    String newNodeProcessing = newValues.getProperty(matricula
		    .toString());
	    RouterTableHandler.addMatriculaAndIpToTable(
		    Integer.parseInt(matricula.toString()), newNodeProcessing);
	}
    }

    private void removePresentMatriculas(Properties newValues) {

	Set<String> commonmatricula = RouterTableHandler
		.commonPresentMatricula(newValues);

	for (String matricula : commonmatricula) {
	    logger.debug("deleting matricula:"+matricula);
	    RouterTableHandler.removeMatriculaFromTable(Integer
		    .parseInt(matricula));
	}
    }

    private void processOnLeaf() {

	Properties newValues = this.getUpdateMultipleRouterTableMessage()
		.getNewValues();
	logger.debug("router table multiple values update  leaf");
	if (this.matriculasPresentInTable()) {

	    logger.debug("is going to remove  the values: " + newValues);
	    removePresentMatriculas(newValues);
	}
    }

    

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy#broadcastDirection()
     */
    @Override
    public BroadcastType broadcastDirection() {

	BroadcastType broadcast = BroadcastType.TWO_WAY;

	if (this.isRoot()) {

	    broadcast = BroadcastType.TURN_BACK;

	} else if (this.isRemove() && this.isMiddle()) {

	    broadcast = BroadcastType.ONE_WAY;

	} else if (this.isLeaf()) {

	    broadcast = BroadcastType.NONE;
	}
	return broadcast;
    }

    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp.BaseStrategy#getTurnBackMessage()
     */
    @Override
    public MessageWrapper getTurnBackMessage() {
	
	OutgoingMessage turnBackMessage = new OutgoingMessage(
		"Change router table multiple",  new Date().getTime());
	SpecificMessage message = this.generateTurnBackOnRoot();
	
	turnBackMessage.setSpecificMessage(message);
	return turnBackMessage;
    }
    
    private SpecificMessage generateTurnBackOnRoot() {
	UpdateMultipleRouterTable updateMsg = null;
	if(this.isRoot()){
	   updateMsg =   this.getUpdateMultipleRouterTableMessage();
	    updateMsg.setUpdateType(UpdateType.REMOVE_ONLY);
	}
	return updateMsg;
    }

}
