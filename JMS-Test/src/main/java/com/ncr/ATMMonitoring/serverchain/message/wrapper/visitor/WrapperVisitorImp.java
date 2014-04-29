package com.ncr.ATMMonitoring.serverchain.message.wrapper.visitor;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.ChainLinkInformation;
import com.ncr.ATMMonitoring.serverchain.NodePosition;
import com.ncr.ATMMonitoring.serverchain.message.SpecificMessage;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.StrategyFactory;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.IncomingMessage;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageWrapper;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.OutgoingMessage;
import com.ncr.ATMMonitoring.serverchain.topicactor.producer.GenericMessageProducer;

/**
 * Concrete implementation of WrapperVisitor
 * 
 * @see WrapperVisitor
 * 
 * @author Otto Abreu
 * 
 */
@Component
public class WrapperVisitorImp implements WrapperVisitor {

    @Resource(name = "outgoingMessageProducer")
    private GenericMessageProducer outgoingProducer;

    @Resource(name = "incomingMessageProducer")
    private GenericMessageProducer incomingProducer;

    @Autowired
    private ChainLinkInformation chainLinkInformation;

    private static final Logger logger = Logger
	    .getLogger(WrapperVisitorImp.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ncr.ATMMonitoring.serverchain.message.wrapper.visitor.WrapperVisitor
     * #visit(com.ncr.ATMMonitoring.serverchain.message.wrapper.IncomingMessage)
     */
    @Override
    public void visit(IncomingMessage message) {
	logger.debug("visiting incoming Message");
	this.applyStrategy(message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ncr.ATMMonitoring.serverchain.message.wrapper.visitor.WrapperVisitor
     * #visit(com.ncr.ATMMonitoring.serverchain.message.wrapper.OutgoingMessage)
     */
    @Override
    public void visit(OutgoingMessage message) {
	logger.debug("visiting outgoing Message");
	this.applyStrategy(message);

    }

    private void applyStrategy(MessageWrapper message) {
	
	SpecificMessage specificMessage = message.getSpecificMessage();
	NodePosition nodePosition = this.chainLinkInformation.getNodePosition();
	
	SpecifcMessageProcessStrategy strategyToAply = this.getStrategyToApply(
		nodePosition, specificMessage);

	if (strategyToAply.canProcessSpecificMessage()) {

	    this.processMessage(strategyToAply, message);

	}
    }

    private void passMessageToNextNode(MessageWrapper message) {

	if (message instanceof OutgoingMessage) {

	    this.outgoingProducer.sendMessage(message);

	} else if (message instanceof IncomingMessage) {

	    this.incomingProducer.sendMessage(message);
	}

    }

    private void processMessage(SpecifcMessageProcessStrategy strategyToAply,
	    MessageWrapper message) {

	strategyToAply.processSpecificMessage();

	if (strategyToAply.passToOtherNode()) {
	    logger.debug("is going to pass message visitor: "+strategyToAply.passToOtherNode());
	    this.passMessageToNextNode(message);
	}
    }

    private SpecifcMessageProcessStrategy getStrategyToApply(
	    NodePosition nodePosition, SpecificMessage message) {

	SpecifcMessageProcessStrategy strategy = StrategyFactory
		.getStrategyForSpecificMessage(message);
	
	strategy.setupStrategy(nodePosition, message);

	return strategy;
    }

}
