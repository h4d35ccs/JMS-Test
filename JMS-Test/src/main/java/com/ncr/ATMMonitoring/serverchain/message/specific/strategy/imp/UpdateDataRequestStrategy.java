package com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.routertable.RouterTableHandler;
import com.ncr.ATMMonitoring.serverchain.message.specific.incoming.UpdateDataResponse;
import com.ncr.ATMMonitoring.serverchain.message.specific.outgoing.UpdateDataRequest;
import com.ncr.serverchain.NodePosition;
import com.ncr.serverchain.message.specific.strategy.BroadcastType;
import com.ncr.serverchain.message.specific.strategy.imp.BaseStrategy;
import com.ncr.serverchain.message.wrapper.IncomingMessage;
import com.ncr.serverchain.message.wrapper.MessageWrapper;

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
@Component
public class UpdateDataRequestStrategy extends BaseStrategy {

    private static final Logger logger = Logger
	    .getLogger(UpdateDataRequestStrategy.class);

    private IncomingMessage turnedBackMessage;

    private boolean finalProcessing = false;

    /*
     * (non-Javadoc)
     * 
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.
     * SpecifcMessageProcessStrategy
     * #setupStrategy(com.ncr.ATMMonitoring.serverchain.NodePosition,
     * com.ncr.ATMMonitoring.serverchain.message.SpecificMessage)
     */
   

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

	boolean matriculaPresentInTable = this
		.matriculaPresentInTable(updateDataMessage);

	if ((this.getCurrentNodePosition().equals(NodePosition.LEAF_NODE) || this
		.getCurrentNodePosition().equals(NodePosition.MIDDLE_NODE))
		&& matriculaPresentInTable) {

	    return true;

	} else {

	    return false;
	}
    }

    private NodePosition getCurrentNodePosition() {
	return this.nodeInformation.getNodePosition();
    }

    private boolean matriculaPresentInTable(UpdateDataRequest updateDataMessage) {
	logger.debug(RouterTableHandler.tableTotring());
	boolean matriculaPresentInTable = RouterTableHandler
		.matriculaIsInRouterTable(updateDataMessage.getMatricula());
	return matriculaPresentInTable;
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

    private void processMessageByPosition() {

	if (isLeaf()) {
	    logger.info(" Update data Request reached leaf, processing....");
	    this.processWhenLeaf();

	}
    }
    

    private void processWhenLeaf() {

	this.finalProcessing = true;

	this.instanciateTurnedBackMessage();
	logger.info(" Update data Request processed in leaf");
    }

    private void instanciateTurnedBackMessage() {
	UpdateDataResponse udr = new UpdateDataResponse();
	udr.setOriginalRequest((UpdateDataRequest) this.messageToProcess);
	
	this.turnedBackMessage = new IncomingMessage("Message incoming from: "
		+ this.nodeInformation.getLocalBrokerUrl(),
		 new Date().getTime());
	
	this.turnedBackMessage.setSpecificMessage(udr);
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

	if (isMiddleNodeAndCanPassMessage()) {

	    passMessage = BroadcastType.ONE_WAY;
	    
	}else if (isLeaf()) {
	    
	    passMessage = BroadcastType.TURN_BACK;
	}

	return passMessage;
    }
    
    private boolean isMiddleNodeAndCanPassMessage(){
	
	if(!this.finalProcessing && canProcessSpecificMessage()){
	    return true;
	    
	}else{
	    
	    return false;
	}
    }

    @Override
    public MessageWrapper getTurnBackMessage() {
	return this.turnedBackMessage;
    }

}
