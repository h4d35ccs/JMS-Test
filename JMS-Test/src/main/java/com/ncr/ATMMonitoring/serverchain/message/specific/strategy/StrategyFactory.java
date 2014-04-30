package com.ncr.ATMMonitoring.serverchain.message.specific.strategy;

import com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.exception.StrategyClassInstantiationException;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.exception.StrategyMapperNotFound;

/**
 * Class that give you the strategy (SpecifcMessageProcessStrategy) associated
 * to a type of SpecificMessage
 * 
 * @author Otto Abreu
 * 
 */
public final class StrategyFactory {

    /**
     * From the given SpecificMessage returns the SpecifcMessageProcessStrategy
     * defined by the annotation StrategyMapper that should be present in the
     * SpecificMessage
     * 
     * @param message SpecificMessage to analyze
     * @return SpecifcMessageProcessStrategy
     */
    public static SpecifcMessageProcessStrategy getStrategyForSpecificMessage(
	    SpecificMessage message) {

	SpecifcMessageProcessStrategy strategy = StrategyFactory
		.loadStrategyByAnnotation(message.getClass());

	return strategy;
    }

    private static SpecifcMessageProcessStrategy loadStrategyByAnnotation(
	    Class<? extends SpecificMessage> messageClass) {


	StrategyMapper mapper = StrategyFactory
		.getAnnotationFromClass(messageClass);
	
	Class<? extends SpecifcMessageProcessStrategy> strategyClassAsociated = mapper
		.strategyMapping();

	SpecifcMessageProcessStrategy strategyAsociatedToMessageInstance = StrategyFactory
		.instantiateStrategyClass(strategyClassAsociated);

	return strategyAsociatedToMessageInstance;
    }
    
    private static StrategyMapper getAnnotationFromClass(
	    Class<? extends SpecificMessage> messageClass) {
	
	StrategyMapper mapper = messageClass
		.getAnnotation(StrategyMapper.class);
	
	StrategyFactory
		.messageClassHaveStrategyAssociated(mapper, messageClass);

	return mapper;
    }

    private static void messageClassHaveStrategyAssociated(
	    StrategyMapper mapper, Class<? extends SpecificMessage> messageClass) {

	if (mapper == null) {
	    throw new StrategyMapperNotFound(
		    StrategyMapperNotFound.MAPPING_ANNOTATION_NOT_FOUND
			    + messageClass);
	}
    }

   

    private static SpecifcMessageProcessStrategy instantiateStrategyClass(
	    Class<? extends SpecifcMessageProcessStrategy> strategyClass) {

	SpecifcMessageProcessStrategy strategyAsociatedToMessage = null;

	try {

	    strategyAsociatedToMessage = strategyClass.newInstance();

	} catch (InstantiationException | IllegalAccessException e) {

	    throw new StrategyClassInstantiationException(
		    StrategyClassInstantiationException.STRATEGYCLASS_INSTANTIATION_ERROR,
		    e);
	}

	return strategyAsociatedToMessage;
    }

}
