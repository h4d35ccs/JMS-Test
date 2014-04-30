package com.ncr.ATMMonitoring.serverchain.message.specific.strategy.exception;

import com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.StrategyMapper;

/**
 * Indicate that a {@link SpecificMessage} does not have an {@link StrategyMapper} defined
 * @author Otto Abreu
 *
 */
public class StrategyMapperNotFound extends RuntimeException {

    /**
     *  MAPPING_ANNOTATION_NOT_FOUND ="The given specific message does not have the annotation StrategyMapper present, can not be procesed";
     */
    public static final String MAPPING_ANNOTATION_NOT_FOUND ="The given specific message does not have the annotation StrategyMapper present, Message can not be procesed: ";
    

    private static final long serialVersionUID = 1L;

    
   
    public StrategyMapperNotFound(String message) {
	super(message);
	
    }


    public StrategyMapperNotFound(Throwable cause) {
	super(cause);
	
    }

}
