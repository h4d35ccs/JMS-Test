/**
 * 
 */
package com.ncr.serverchain.message.specific.strategy.imp;

import org.apache.log4j.Logger;


import com.ncr.serverchain.message.specific.strategy.BroadcastType;

/**
 * 
 * Abstract class that force to pass the message until the root is reached
 * @author Otto Abreu
 * 
 */
public abstract class DirectRootCommunicationStrategy extends BaseStrategy {

    private static final Logger logger = Logger
	    .getLogger(DirectRootCommunicationStrategy.class);

    /*
     * (non-Javadoc)
     * @see com.ncr.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#canProcessSpecificMessage()
     */
    @Override
    public boolean canProcessSpecificMessage() {
	// always true because is going up
	// each parent should be able to process the message
	// until it reaches the root node
	return true;
    }

    /*
     * (non-Javadoc)
     * @see com.ncr.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#processSpecificMessage()
     */
    @Override
    public void processSpecificMessage() {

	this.processMessageWhenReachedRoot();
    }

    
    private void processMessageWhenReachedRoot() {
	if (isRoot()) {

	    logger.debug("Processing message in root: " + this.messageToProcess);

	    this.customLogicToProcessMessageInRoot();

	} else {

	    logger.debug("middle node passing response message: "
		    + this.messageToProcess);
	}

    }

    /**
     * Custom logic of each class to implement
     */
    protected abstract void customLogicToProcessMessageInRoot();

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ncr.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy
     * #broadcastDirection()
     */
    @Override
    public BroadcastType broadcastDirection() {

	BroadcastType broadcastDirection = BroadcastType.ONE_WAY;

	if (isRoot()) {

	    broadcastDirection = BroadcastType.NONE;
	}
	return broadcastDirection;
    }

}
