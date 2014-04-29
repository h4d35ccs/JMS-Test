package com.ncr.ATMMonitoring.serverchain.message.specific.strategy;

import com.ncr.ATMMonitoring.serverchain.NodePosition;
import com.ncr.ATMMonitoring.serverchain.message.SpecificMessage;

/**
 * <pre>
 * Define an strategy to process an SpecifcMessage
 * In order to start the strategy, call fist the setup method
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
     * if true, it will try to
     * pass the message to the next/previous node.
     * 
     * @return boolean
     * </pre>
     **/
    boolean passToOtherNode();
    
    
   

}
