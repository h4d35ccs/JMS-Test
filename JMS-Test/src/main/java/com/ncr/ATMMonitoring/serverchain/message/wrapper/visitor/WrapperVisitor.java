package com.ncr.ATMMonitoring.serverchain.message.wrapper.visitor;

import com.ncr.ATMMonitoring.serverchain.message.wrapper.IncomingMessage;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.OutgoingMessage;

/**
 * <pre>
 * Visitor class that Declares an operation for each individual Wrapper
 * 
 *  is part of an visitor pattern implementation
 * 
 *  @see  <a href="http://en.wikipedia.org/wiki/Visitor_pattern"> Visitor pattern in wikipedia</a>
 * 
 *  @author Otto Abreu
 * </pre>
 */
public interface WrapperVisitor {
    /**
     * The visit method that handles IncomingMessage
     * @see  <a href="http://en.wikipedia.org/wiki/Visitor_pattern"> Visitor pattern in wikipedia</a>
     * @param message
     */
    void visit(IncomingMessage message);
    /**
     * The visit method that handles OutgoingMessage
     * @see  <a href="http://en.wikipedia.org/wiki/Visitor_pattern"> Visitor pattern in wikipedia</a>
     * @param message
     */
    void visit(OutgoingMessage message);

}
