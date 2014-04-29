package com.ncr.ATMMonitoring.serverchain.message.specific.outgoing;

import com.ncr.ATMMonitoring.serverchain.message.SpecificMessage;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.StrategyMapper;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp.UpdateDataRequestStrategy;

/**
 * Message to request an update to an ATM
 * @author Otto Abreu
 *
 */
@StrategyMapper(strategyMapping = UpdateDataRequestStrategy.class)
public class UpdateDataRequest implements SpecificMessage {
    
 
    private static final long serialVersionUID = 1L;

    private String atmIp;
    
    private int matricula;
    
 
    public UpdateDataRequest(String atmIp, int matricula) {
	super();
	this.atmIp = atmIp;
	this.matricula = matricula;
    }

    public String getAtmIp() {
        return atmIp;
    }

    public int getMatricula() {
        return matricula;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
	StringBuffer sb = new StringBuffer("");
	sb.append("atmIp: ");
	sb.append(atmIp);
	sb.append(" ");
	sb.append("matricula:");
	sb.append(matricula);
	return sb.toString();
    }

}