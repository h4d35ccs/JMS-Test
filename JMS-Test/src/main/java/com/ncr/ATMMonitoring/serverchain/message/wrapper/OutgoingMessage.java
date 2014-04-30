package com.ncr.ATMMonitoring.serverchain.message.wrapper;

import com.ncr.ATMMonitoring.serverchain.message.wrapper.visitor.WrapperVisitor;


/**
 * <pre>
 * Define the wrapper used to outgoing messages.
 * 
 * The outgoing message is a message that starts in a root node and ends in a leaf
 * @author Otto Abreu
 * 
 * </pre>
 *
 */
public class OutgoingMessage extends MessageWrapper {

    private static final long serialVersionUID = 1L;

    public OutgoingMessage(String message, int id) {
	super(message, id);

    }
    
    @Override
    public void accept(WrapperVisitor visitor) {
	visitor.visit(this);

    }

}
