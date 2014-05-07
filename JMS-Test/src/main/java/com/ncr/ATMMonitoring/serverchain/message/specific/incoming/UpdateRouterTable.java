package com.ncr.ATMMonitoring.serverchain.message.specific.incoming;

import com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.StrategyMapper;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp.UpdateRouterTableStrategy;
/**
 * message that indicate that an update must be done in the routerTable
 * @author Otto Abreu
 *
 */

@StrategyMapper(strategyMapping = UpdateRouterTableStrategy.class)
public class UpdateRouterTable implements SpecificMessage{
    
    private static final long serialVersionUID = 1L;
    
    private int matricula;
    private String newFinalNodeInCharge;
    private UpdateType updateType = UpdateType.UPDATE;
   
    
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
    
    
    
    public UpdateType getUpdateType() {
        return updateType;
    }

    public void setUpdateType(UpdateType updateType) {
        this.updateType = updateType;
    }
    
    /**
     * Indicate if this update is for adding (default) or for removing
     * @author Otto Abreu
     *
     */
    public enum UpdateType{
	UPDATE, REMOVE_ONLY, FORCE_UPDATE_FROM_ROOT;
    }

}
