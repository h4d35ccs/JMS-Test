package com.ncr.serverchain.message.specific.strategy;

import org.springframework.context.ApplicationContext;

import com.ncr.serverchain.message.specific.SpecificMessage;
import com.ncr.serverchain.message.wrapper.MessageWrapper;

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
     * @param message SpecificMessage Message to analyze
     * @param ApplicationContext spring context for initialize spring beans

     */
    void setupStrategy( SpecificMessage message, ApplicationContext springContext);
    
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
     * THE IMPLEMENTATION SHOULD NOT RETURN NULL
     * 
     * @return BroadcastType
     * </pre>
     **/
    BroadcastType broadcastDirection();
    
    /**
     * <pre>
     * When the strategy determined that the message should go back, this method return the new message to send.
     * 
     * * If the original message was Outgoing this method will construct a new Incoming message
     * * If the original message was Incoming this method will construct a new Outgoing message
     * 
     * ONLY Return null if not implemented!
     * 
     * </pre>
     * @return MessageWrapper
     */
    MessageWrapper getTurnBackMessage();
    
    /**
     * When the broadcast is set to TWO_WAY is possible to send another
     * message in the other direction
     * @return SpecificMessage
     */
    SpecificMessage getChangeDirectionMessageInTwoWay();

}
