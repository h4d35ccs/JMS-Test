package com.ncr.ATMMonitoring.serverchain.message.specific.incoming;

import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp.UpdateRouterTableStrategy;
import com.ncr.serverchain.message.specific.UpdateRouterTableMessage;
import com.ncr.serverchain.message.specific.strategy.StrategyMapper;
/**
 * message that indicate that an update must be done in the routerTable
 * @author Otto Abreu
 *
 */

@StrategyMapper(strategyMapping = UpdateRouterTableStrategy.class)
public class UpdateRouterTable extends UpdateRouterTableMessage{
    
    private static final long serialVersionUID = 1L;
    
    private long matricula;
    private String newFinalNodeInCharge;
   
    public UpdateRouterTable(long matricula, String newFinalNodeInCharge) {
	super();
	this.matricula = matricula;
	this.newFinalNodeInCharge = newFinalNodeInCharge;

    }

    public long getMatricula() {
        return matricula;
    }
   
    public String getNewFinalNodeInCharge() {
        
	return newFinalNodeInCharge;
    }

}
