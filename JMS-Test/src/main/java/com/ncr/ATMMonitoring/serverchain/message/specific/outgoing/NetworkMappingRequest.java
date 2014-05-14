package com.ncr.ATMMonitoring.serverchain.message.specific.outgoing;

import com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.StrategyMapper;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp.NetworkMappingRequestStrategy;

/**
 * Represents a Network Mapping Request
 * @author Otto Abreu
 *
 */
@StrategyMapper(strategyMapping = NetworkMappingRequestStrategy.class)
public class NetworkMappingRequest implements SpecificMessage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}
