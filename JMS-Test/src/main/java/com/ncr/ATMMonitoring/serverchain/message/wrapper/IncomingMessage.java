/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.message.wrapper;

import com.ncr.ATMMonitoring.serverchain.message.wrapper.visitor.WrapperVisitor;


/**
 * <pre>
 * Define the wrapper used to Incoming messages.
 * 
 * The Incoming message is a message that starts in a leaf node and ends in a root
 * @author Otto Abreu
 * 
 * </pre>
 *
 */
public class IncomingMessage extends MessageWrapper {

 
    private static final long serialVersionUID = -6457607990812054891L;

    public IncomingMessage(String message, int id) {
	super(message, id);

    }

    @Override
    public void accept(WrapperVisitor visitor) {
	visitor.visit(this);

    }
}
