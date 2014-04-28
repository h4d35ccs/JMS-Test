package com.ncr.ATMMonitoring.serverchain.message.wrapper.visitor;

/**
 * @author Otto Abreu
 *
 */
public interface VisitableMessageWrapper {
    
    void accept(WrapperVisitor visitor);

}
