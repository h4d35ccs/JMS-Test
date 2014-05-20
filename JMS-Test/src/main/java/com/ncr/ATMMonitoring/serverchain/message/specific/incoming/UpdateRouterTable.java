package com.ncr.ATMMonitoring.serverchain.message.specific.incoming;

import com.ncr.ATMMonitoring.serverchain.message.specific.UpdateRouterTableMessage;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.StrategyMapper;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp.UpdateRouterTableStrategy;
/**
 * message that indicate that an update must be done in the routerTable
 * @author Otto Abreu
 *
 */

@StrategyMapper(strategyMapping = UpdateRouterTableStrategy.class)
public class UpdateRouterTable extends UpdateRouterTableMessage{
    
    private static final long serialVersionUID = 1L;
    
    private int matricula;
    private String newFinalNodeInCharge;
   
    public UpdateRouterTable(int matricula, String newFinalNodeInCharge) {
	super();
	this.matricula = matricula;
	this.newFinalNodeInCharge = newFinalNodeInCharge;

    }

    public int getMatricula() {
        return matricula;
    }
   
    public String getNewFinalNodeInCharge() {
        
	return newFinalNodeInCharge;
    }

}
