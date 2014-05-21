package com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp;

import org.apache.log4j.Logger;

import com.ncr.serverchain.message.specific.strategy.BroadcastType;
import com.ncr.serverchain.message.specific.strategy.imp.BaseStrategy;

/**
 * @author Otto Abreu
 *
 */
public class UpdateDataResponseStrategy extends
	BaseStrategy {

 
    
    private static final Logger logger = Logger
	    .getLogger(UpdateDataResponseStrategy.class);
    

    /* (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#canProcessSpecificMessage()
     */
    @Override
    public boolean canProcessSpecificMessage() {
	// always true because is going up
	// each parent should be able to process the message
	//until it reaches the root node
	return true;
    }

    /* (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#processSpecificMessage()
     */
    @Override
    public void processSpecificMessage() {
	
	this.processMessageWhenReachedRoot();
    }
    
    
    private void processMessageWhenReachedRoot(){
	
	if (isRoot()) {
	    logger.debug("Processing message in root: "+this.messageToProcess);
	}else{
	    logger.debug("Doing nothing  in middle node: "+this.messageToProcess);
	}
    }
    
   
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#broadcastDirection()
     */
    @Override
    public BroadcastType broadcastDirection() {
	
	BroadcastType broadcastDirection = BroadcastType.ONE_WAY;
	
	if(isRoot()){
	   
	    broadcastDirection = BroadcastType.NONE;
	}
	return broadcastDirection;
    }
    

}
