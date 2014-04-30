package com.ncr.ATMMonitoring.serverchain.message.specific.incoming;

import com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage;
import com.ncr.ATMMonitoring.serverchain.message.specific.outgoing.UpdateDataRequest;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.StrategyMapper;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp.UpdateDataResponseStrategy;

/**
 * Holds the  Update response from an ATM 
 * @author Otto Abreu
 *
 */
@StrategyMapper(strategyMapping = UpdateDataResponseStrategy.class)
public class UpdateDataResponse implements SpecificMessage{

 
    private static final long serialVersionUID = 1L;

    private UpdateDataRequest originalRequest;
    
    private String jsonMessage;

    
    public UpdateDataRequest getOriginalRequest() {
        return originalRequest;
    }

    public String getJsonMessage() {
        return jsonMessage;
    }
    
    public String getAtmIp() {
        return originalRequest.getAtmIp();
    }

    public int getMatricula() {
        return originalRequest.getMatricula();
    }
    
}
