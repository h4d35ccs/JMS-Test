package com.ncr.ATMMonitoring.serverchain.message.wrapper.visitor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.StrategyExecutor;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.IncomingMessage;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageWrapper;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.OutgoingMessage;

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

    @Autowired
    private StrategyExecutor strategyExecutor;

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
	this.strategyExecutor.execute(message);

    }

}
