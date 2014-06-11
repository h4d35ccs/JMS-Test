package com.ncr.serverchain.message.specific.strategy.imp;

import com.ncr.serverchain.message.specific.strategy.BroadcastType;

/**
 * Strategy that execute logic in leaf nodes
 * @author Otto Abreu
 *
 */
public abstract class DirectLeafNodeCommunication extends BaseStrategy{

    /*
     * (non-Javadoc)
     * @see com.ncr.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#canProcessSpecificMessage()
     */
    @Override
    public boolean canProcessSpecificMessage() {
	
	return true;
    }

    /*
     * (non-Javadoc)
     * @see com.ncr.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#processSpecificMessage()
     */
    @Override
    public void processSpecificMessage() {
	
	if(this.isLeaf()){
	   
	    this.processInLeaf();
	}
	
    }

    @Override
    public BroadcastType broadcastDirection() {
	
	BroadcastType broadcast = BroadcastType.NONE;
	
	if(!this.isLeaf()){
	    broadcast = BroadcastType.ONE_WAY;
	}
	return broadcast;
    }
    /**
     * custom logic to apply in leaf nodes
     */
    protected abstract void processInLeaf();

}
