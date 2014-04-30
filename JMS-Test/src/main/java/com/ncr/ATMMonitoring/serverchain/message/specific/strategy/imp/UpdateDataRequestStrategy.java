package com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp;

import org.apache.log4j.Logger;

import com.ncr.ATMMonitoring.routertable.RouterTableHandler;
import com.ncr.ATMMonitoring.serverchain.NodePosition;
import com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage;
import com.ncr.ATMMonitoring.serverchain.message.specific.outgoing.UpdateDataRequest;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.BroadcastType;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy;

/**
 * <pre>
 * Strategy to execute to UpdateRequest Messages.
 * 
 * This strategy will continue passing the message if the node can process ( the matricula is on the route table) 
 * and is not a leaf, if is a leaf will call the class in charge of calling the ATM
 * @author Otto Abreu
 * 
 * </pre>
 * 
 */
public class UpdateDataRequestStrategy implements SpecifcMessageProcessStrategy {

    private static final Logger logger = Logger
	    .getLogger(UpdateDataRequestStrategy.class);

    private NodePosition nodePosition;
    private SpecificMessage messageToProcess;

    private boolean finalProcessing = false;

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy
     * #setupStrategy(com.ncr.ATMMonitoring.serverchain.NodePosition,
     * com.ncr.ATMMonitoring.serverchain.message.SpecificMessage)
     */
    @Override
    public void setupStrategy(NodePosition postion, SpecificMessage message) {
	this.nodePosition = postion;
	this.messageToProcess = message;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy#canProcessSpecificMessage()
     */
    @Override
    public boolean canProcessSpecificMessage() {

	boolean canProcess = false;
	UpdateDataRequest updateDataMessage = (UpdateDataRequest) this.messageToProcess;

	if (nodeIsLeafOrMiddleAndMatriculaIsPresent(updateDataMessage)) {

	    canProcess = true;
	}

	logger.debug("can process the message? " + canProcess);
	return canProcess;
    }

    private boolean nodeIsLeafOrMiddleAndMatriculaIsPresent(
	    UpdateDataRequest updateDataMessage) {

	boolean matriculaPresentInTable = RouterTableHandler
		.matriculaIsInRouterTable(updateDataMessage.getMatricula());

	if ((this.nodePosition.equals(NodePosition.LEAF_NODE) || this.nodePosition
		.equals(NodePosition.MIDDLE_NODE)) && matriculaPresentInTable) {
	   
	    return true;

	} else {

	    return false;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy#processSpecificMessage()
     */
    @Override
    public void processSpecificMessage() {

	this.processMessageByPosition();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy#broadcastDirection()
     */
    @Override
    public BroadcastType broadcastDirection() {
	
	BroadcastType passMessage = BroadcastType.NONE;

	if (!this.finalProcessing && canProcessSpecificMessage()) {

	    passMessage = BroadcastType.ONE_WAY;
	}
	
	return passMessage;
    }

    private void processMessageByPosition() {

	if (this.nodePosition.equals(NodePosition.LEAF_NODE)) {
	    logger.debug("reached leaf");
	    this.processWhenLeaf();

	}
    }

    private void processWhenLeaf() {

	this.finalProcessing = true;
	logger.debug("Processing in leaf:" + this.messageToProcess);
    }

}
