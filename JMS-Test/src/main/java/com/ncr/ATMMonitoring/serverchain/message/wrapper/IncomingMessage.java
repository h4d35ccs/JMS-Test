/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.message.wrapper;

import com.ncr.ATMMonitoring.serverchain.message.wrapper.visitor.WrapperVisitor;


/**
 * @author Otto Abreu
 * 
 */
public class IncomingMessage extends MessageWrapper {

    /**
     * 
     */
    private static final long serialVersionUID = -6457607990812054891L;

    public IncomingMessage(String message, int id) {
	super(message, id);

    }

    @Override
    public void accept(WrapperVisitor visitor) {
	visitor.visit(this);

    }
}
