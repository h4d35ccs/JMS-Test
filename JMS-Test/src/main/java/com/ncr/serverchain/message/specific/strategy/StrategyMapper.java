package com.ncr.serverchain.message.specific.strategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define the SpecifcMessageProcessStrategy that should be used to process an
 * SpecificMessage
 * 
 * @author Otto Abreu
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface StrategyMapper {

    /**
     * Indicate the class that should be instantiated to process the message
     * 
     * @return Class<? extends SpecifcMessageProcessStrategy>
     */
    Class<? extends SpecifcMessageProcessStrategy> strategyMapping();

}
