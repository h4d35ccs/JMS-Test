package com.ncr.serverchain.message.wrapper;

import com.ncr.serverchain.message.wrapper.visitor.WrapperVisitor;


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

    
    public OutgoingMessage(String message, Long id) {
	super(message, id);

    }
    
    public OutgoingMessage(Long id) {
   	super(DEFAULT_OUTGOINGMESSAGE_INNER_MESSAGE, id);

    }
    
    @Override
    public void accept(WrapperVisitor visitor) {
	visitor.visit(this);

    }

}
