package com.ncr.ATMMonitoring.serverchain.message.specific.incoming;

import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp.UpdateRequestCommunicationErrorStrategy;
import com.ncr.serverchain.message.specific.SpecificMessage;
import com.ncr.serverchain.message.specific.strategy.StrategyMapper;

/**
 * @author Otto Abreu
 *
 */
@StrategyMapper(strategyMapping=UpdateRequestCommunicationErrorStrategy.class)
public class UpdateRequestCommunicationError implements SpecificMessage {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String atmIp;
    
    private Long matricula;
    
    private String processingNode;

    
    
    public String getAtmIp() {
        return atmIp;
    }



    public Long getMatricula() {
        return matricula;
    }



    public String getProcessingNode() {
        return processingNode;
    }



    public UpdateRequestCommunicationError(String atmIp, Long matricula,
	    String processingNode) {
	super();
	this.atmIp = atmIp;
	this.matricula = matricula;
	this.processingNode = processingNode;
    }

}
