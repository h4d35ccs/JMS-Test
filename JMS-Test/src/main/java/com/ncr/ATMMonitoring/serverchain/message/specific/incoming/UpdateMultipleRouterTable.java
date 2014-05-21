package com.ncr.ATMMonitoring.serverchain.message.specific.incoming;

import java.util.Properties;

import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp.UpdateMultipleRouterTableStrategy;
import com.ncr.serverchain.message.specific.UpdateRouterTableMessage;
import com.ncr.serverchain.message.specific.strategy.StrategyMapper;

/**
 * Message used to update several records in the router table
 * @author Otto Abreu
 *
 */
@StrategyMapper(strategyMapping=UpdateMultipleRouterTableStrategy.class)
public class UpdateMultipleRouterTable extends UpdateRouterTableMessage{

   
    private static final long serialVersionUID = 1L;
    
    
    private Properties newValues;


    public UpdateMultipleRouterTable(Properties newValues) {
	super();
	this.newValues = newValues;
    }


    public Properties getNewValues() {
        return newValues;
    } 
    
    

}
