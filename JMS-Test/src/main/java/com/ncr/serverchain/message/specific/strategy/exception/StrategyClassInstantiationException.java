package com.ncr.serverchain.message.specific.strategy.exception;

import com.ncr.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy;

/**
 * Indicate an error while loading dynamically an instance of {@link SpecifcMessageProcessStrategy }
 * @author Otto Abreu
 *
 */
public class StrategyClassInstantiationException extends RuntimeException {

    
    private static final long serialVersionUID = 1L;
    /**
     * STRATEGYCLASS_INSTANTIATION_ERROR="Can not instantiate the strategy class due the following exception: ";
     */
    public static final String STRATEGYCLASS_INSTANTIATION_ERROR="Can not instantiate the strategy class due the following exception: ";

    
    public StrategyClassInstantiationException(String message, Throwable cause) {
	super(message, cause);
   }


}
