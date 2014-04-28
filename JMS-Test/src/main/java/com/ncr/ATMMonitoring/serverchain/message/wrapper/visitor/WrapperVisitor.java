package com.ncr.ATMMonitoring.serverchain.message.wrapper.visitor;

import com.ncr.ATMMonitoring.serverchain.message.wrapper.IncomingMessage;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.OutgoingMessage;

/**
 * @author Otto Abreu
 *
 */
public interface WrapperVisitor  {
    
    void visit(IncomingMessage message);
    
    void visit(OutgoingMessage message);

}
