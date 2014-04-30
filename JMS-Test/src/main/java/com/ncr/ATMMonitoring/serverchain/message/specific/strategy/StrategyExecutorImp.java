package com.ncr.ATMMonitoring.serverchain.message.specific.strategy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.MessagePublisher;
import com.ncr.ATMMonitoring.serverchain.NodeInformation;
import com.ncr.ATMMonitoring.serverchain.NodePosition;
import com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.IncomingMessage;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageWrapper;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.OutgoingMessage;

/**
 * Concrete implementation of StrategyExecutor
 * 
 * @author Otto Abreu
 * 
 */
@Component
public class StrategyExecutorImp implements StrategyExecutor {

    private static final Logger logger = Logger
	    .getLogger(StrategyExecutorImp.class);

    // @Resource(name = "outgoingMessageProducer")
    // private GenericMessageProducer outgoingProducer;
    //
    // @Resource(name = "incomingMessageProducer")
    // private GenericMessageProducer incomingProducer;

    @Autowired
    private MessagePublisher messagePublisher;

    @Autowired
    private NodeInformation nodeInformation;

    private SpecifcMessageProcessStrategy strategyToAply;

    private MessageWrapper messageWrapper;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ncr.ATMMonitoring.serverchain.message.specific.strategy.StrategyExecutor
     * #
     * execute(com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageWrapper)
     */
    public void execute(MessageWrapper message) {

	this.strategyExecutorSetup(message);

	if (this.strategyToAply.canProcessSpecificMessage()) {

	    this.processMessage(this.messageWrapper);

	}
    }

    private void strategyExecutorSetup(MessageWrapper message) {

	SpecificMessage specificMessage = message.getSpecificMessage();
	NodePosition nodePosition = this.nodeInformation.getNodePosition();

	this.messageWrapper = message;

	this.setupStrategyToUse(specificMessage, nodePosition);

    }

    private void setupStrategyToUse(SpecificMessage specificMessage,
	    NodePosition nodePosition) {

	this.strategyToAply = StrategyFactory
		.getStrategyForSpecificMessage(specificMessage);

	this.strategyToAply.setupStrategy(nodePosition, specificMessage);

    }

    private void processMessage(MessageWrapper message) {

	this.strategyToAply.processSpecificMessage();

	this.broadcastMessage(message);
    }

    private void broadcastMessage(MessageWrapper message) {
	logger.debug("is going to broadcast message : "
		+ strategyToAply.broadcastDirection());

	if (isBroadcastOneWay()) {

	    this.broadcastMessageOneWay(message);

	} else if (isBroadcastTwoWay()) {

	    this.broadcastMessageTwoWay(message);
	}
    }

    private boolean isBroadcastOneWay() {

	if (strategyToAply.broadcastDirection().equals(BroadcastType.ONE_WAY)) {

	    return true;

	} else {

	    return false;
	}
    }

    private boolean isBroadcastTwoWay() {

	if (this.strategyToAply.broadcastDirection().equals(
		BroadcastType.TWO_WAY)) {

	    return true;

	} else {

	    return false;
	}
    }

    private void broadcastMessageOneWay(MessageWrapper message) {

	if (message instanceof OutgoingMessage) {

	    this.messagePublisher.publishOutgoingMessage(message);

	} else if (message instanceof IncomingMessage) {

	    this.messagePublisher.publishIncomingMessage(message);
	}

    }

    private void broadcastMessageTwoWay(MessageWrapper message) {

	this.broadcastMessageOneWay(message);

	this.broadcastSecondWay(message);
    }

    private void broadcastSecondWay(MessageWrapper message) {

	if (message instanceof OutgoingMessage) {

	    this.broadcastSwitchedOutgoingMessage(message);

	} else if (message instanceof IncomingMessage) {

	    this.broadcastSwitchedIncomingMessage(message);
	}

    }

    private void broadcastSwitchedOutgoingMessage(MessageWrapper message) {

	OutgoingMessage originalOutgoingMessage = (OutgoingMessage) message;

	IncomingMessage inSwitchedMessage = this
		.switchMessageTypeFromOutgoingToIncoming(originalOutgoingMessage);

	this.messagePublisher.publishIncomingMessage(inSwitchedMessage);
    }

    private IncomingMessage switchMessageTypeFromOutgoingToIncoming(
	    OutgoingMessage message) {

	String originalOutgoingMessage = message.getMessage();
	int originalOutgoingId = message.getId();
	SpecificMessage originalSpecificMessage = message.getSpecificMessage();

	IncomingMessage incomingMessage = new IncomingMessage(
		originalOutgoingMessage, originalOutgoingId);
	incomingMessage.setSpecificMessage(originalSpecificMessage);

	return incomingMessage;
    }

    private void broadcastSwitchedIncomingMessage(MessageWrapper message) {

	IncomingMessage originalIncomingMessage = (IncomingMessage) message;
	OutgoingMessage outSwitchedMessage = this
		.switchMessageTypeFromIncomingToOutgoing(originalIncomingMessage);

	this.messagePublisher.publishOutgoingMessage(outSwitchedMessage);
    }

    private OutgoingMessage switchMessageTypeFromIncomingToOutgoing(
	    IncomingMessage message) {

	String originalIncomingMessage = message.getMessage();
	int originalIncomingId = message.getId();
	SpecificMessage originalSpecificMessage = message.getSpecificMessage();

	OutgoingMessage outmessage = new OutgoingMessage(
		originalIncomingMessage, originalIncomingId);
	outmessage.setSpecificMessage(originalSpecificMessage);

	return outmessage;
    }

}
