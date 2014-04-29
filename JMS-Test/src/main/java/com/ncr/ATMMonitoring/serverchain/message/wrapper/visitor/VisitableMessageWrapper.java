package com.ncr.ATMMonitoring.serverchain.message.wrapper.visitor;

/**
 * <pre>
 *  Define the behavior that allows a wrapper to accepts visitors.
 *  
 *  Is part of a Visitor Pattern Implementation
 *  
 * @see  <a href="http://en.wikipedia.org/wiki/Visitor_pattern"> Visitor pattern in wikipedia</a>
 * 
 * @author Otto Abreu
 * 
 * <pre>
 */
public interface VisitableMessageWrapper {

    /**
     * The accept method that sets a visitor into a visitable object.
     * is an standard for the visitor pattern
     * 
     * @see  <a href="http://en.wikipedia.org/wiki/Visitor_pattern"> Visitor pattern in wikipedia</a>
     * @param visitor WrapperVisitor
     */
    void accept(WrapperVisitor visitor);

}
