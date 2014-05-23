package com.ncr.ATMMonitoring.serverchain.message.specific.incoming;

import com.ncr.ATMMonitoring.serverchain.message.specific.outgoing.UpdateDataRequest;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp.UpdateDataResponseStrategy;
import com.ncr.serverchain.message.specific.SpecificMessage;
import com.ncr.serverchain.message.specific.strategy.StrategyMapper;

/**
 * Holds the  Update response from an ATM 
 * @author Otto Abreu
 *
 */
@StrategyMapper(strategyMapping = UpdateDataResponseStrategy.class)
public class UpdateDataResponse implements SpecificMessage{

 
    public UpdateDataResponse(UpdateDataRequest originalRequest,
	    String jsonMessage) {
	super();
	this.originalRequest = originalRequest;
	this.jsonMessage = jsonMessage;
    }
    

    public UpdateDataResponse() {
	super();
    }

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

    public Long getMatricula() {
        return originalRequest.getMatricula();
    }


    public void setOriginalRequest(UpdateDataRequest originalRequest) {
        this.originalRequest = originalRequest;
    }


    public void setJsonMessage(String jsonMessage) {
        this.jsonMessage = jsonMessage;
    }
    
    
}
