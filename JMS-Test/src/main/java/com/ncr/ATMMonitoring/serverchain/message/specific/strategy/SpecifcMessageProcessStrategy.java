package com.ncr.ATMMonitoring.serverchain.message.specific.strategy;

import com.ncr.ATMMonitoring.serverchain.NodePosition;
import com.ncr.ATMMonitoring.serverchain.message.SpecificMessage;

/**
 * <pre>
 * Define an strategy to process an SpecifcMessage.
 * In order to start the strategy, call fist the setup method.
 * 
 * The strategy must define the broadcast direction 
 * 
 * @author Otto Abreu
 * </pre>
 */
public interface SpecifcMessageProcessStrategy {
    
    /**
     * Sets the objects needed to execute a strategy
     * @param postion NodePosition that indicate the actual position of the node
     * @param message SpecificMessage Message to analyze
     */
    void setupStrategy(NodePosition postion, SpecificMessage message);
    
    /**
     * True if this node should process the message
     * 
     * @return boolean
     */
    boolean canProcessSpecificMessage();

    /**
     * <pre>
     * Logic to process the message.
     * 
     * </pre>
     */
    void processSpecificMessage();

    /**
     * <pre>
     * Indicate the way this strategy will broadcast the message.
     * The broadcast could be:
     * * One way: 
     * 		* if is a incoming message: will broadcast only to the parent node
     * 		* if is a outgoing message: will broadcast to all children
     * 
     * * two way: will broadcast to the parent and children
     * 
     * * NONE: wil not broadcast the message to any other node
     * 
     * @return BroadcastType
     * </pre>
     **/
    BroadcastType broadcastDirection();
    

}
