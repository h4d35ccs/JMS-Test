package com.ncr.serverchain.message.specific.strategy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ncr.serverchain.MessagePublisher;
import com.ncr.serverchain.message.specific.SpecificMessage;
import com.ncr.serverchain.message.wrapper.IncomingMessage;
import com.ncr.serverchain.message.wrapper.MessageWrapper;
import com.ncr.serverchain.message.wrapper.OutgoingMessage;

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

    @Autowired
    private MessagePublisher messagePublisher;

    @Autowired
    private ApplicationContext springContext;

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

	this.messageWrapper = message;

	this.setupStrategyToUse(specificMessage);

    }

    private void setupStrategyToUse(SpecificMessage specificMessage) {

	this.strategyToAply = StrategyFactory
		.getStrategyForSpecificMessage(specificMessage);

	this.strategyToAply.setupStrategy(specificMessage, this.springContext);

    }

    private void processMessage(MessageWrapper message) {

	this.strategyToAply.processSpecificMessage();

	this.broadcastMessage(message);
    }

    private void broadcastMessage(MessageWrapper message) {
	logger.info("is going to broadcast message : "
		+ strategyToAply.broadcastDirection());

	if (isBroadcastOneWay()) {

	    this.broadcastMessageOneWay(message);

	} else if (isBroadcastTwoWay()) {

	    this.broadcastMessageTwoWay(message);

	} else if (isBroadcastTurnBack()) {

	    this.bradcastTurnBack();
	}
    }

    private boolean isBroadcastOneWay() {

	if (strategyToAply.broadcastDirection().equals(BroadcastType.ONE_WAY)) {

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

    private boolean isBroadcastTwoWay() {

	if (this.strategyToAply.broadcastDirection().equals(
		BroadcastType.TWO_WAY)) {

	    return true;

	} else {

	    return false;
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

    private boolean isBroadcastTurnBack() {

	if (this.strategyToAply.broadcastDirection().equals(
		BroadcastType.TURN_BACK)) {
	    return true;
	} else {
	    return false;
	}
    }

    private void bradcastTurnBack() {
	MessageWrapper wrapper = this.strategyToAply.getTurnBackMessage();
	this.broadcastMessageOneWay(wrapper);
    }

    private IncomingMessage switchMessageTypeFromOutgoingToIncoming(
	    OutgoingMessage message) {

	String originalOutgoingMessage = message.getMessage();
	long originalOutgoingId = message.getId();

	SpecificMessage specificMessageToBroadcastTwoWay = this
		.getSpecificMessageToBrodcastSecondWay(message);
	IncomingMessage incomingMessage = new IncomingMessage(
		"switched to incoming " + originalOutgoingMessage,
		originalOutgoingId);
	incomingMessage.setSpecificMessage(specificMessageToBroadcastTwoWay);

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
	long originalIncomingId = message.getId();

	SpecificMessage originalSpecificMessage = this
		.getSpecificMessageToBrodcastSecondWay(message);

	OutgoingMessage outmessage = new OutgoingMessage(
		"switched to outgoing " + originalIncomingMessage,
		originalIncomingId);
	outmessage.setSpecificMessage(originalSpecificMessage);

	return outmessage;
    }

    private SpecificMessage getSpecificMessageToBrodcastSecondWay(
	    MessageWrapper wrapper) {

	SpecificMessage messageToBrodcastSecondWay = this.strategyToAply
		.getChangeDirectionMessageInTwoWay();

	if (messageToBrodcastSecondWay == null) {
	    messageToBrodcastSecondWay = wrapper.getSpecificMessage();
	}
	return messageToBrodcastSecondWay;
    }

}
