package com.ncr.ATMMonitoring.serverchain.message.specific.outgoing;



import java.util.Properties;

import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp.ChangeLeafNodeRouterTableStrategy;
import com.ncr.serverchain.message.specific.DirectCommunicationMessage;
import com.ncr.serverchain.message.specific.strategy.StrategyMapper;


/**
 * Message used to change all the Router table in a node
 * 
 * @author Otto Abreu
 *
 */
@StrategyMapper(strategyMapping = ChangeLeafNodeRouterTableStrategy.class )
public class ChangeLeafNodeRouterTableRequest extends DirectCommunicationMessage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    
    private Properties newRouterTable;



    public ChangeLeafNodeRouterTableRequest(String nodeUrlAndPort,Properties newRouterTable) {
	super(nodeUrlAndPort);
	this.newRouterTable = newRouterTable;
    }



    public Properties getNewRouterTable() {
        return newRouterTable;
    }
    
    

}
