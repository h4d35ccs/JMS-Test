package com.ncr.ATMMonitoring.serverchain.message.specific.strategy;

import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageWrapper;

/**
 * Executes an strategy for a SpecifcMessageProcessStrategy for the given message 
 * @author Otto Abreu
 *
 */
public interface StrategyExecutor {
    
    /**
     * Execute the strategy loaded by the executor to the given message
     * @param message MessageWrapper
     */
    public void execute(MessageWrapper message);

}
