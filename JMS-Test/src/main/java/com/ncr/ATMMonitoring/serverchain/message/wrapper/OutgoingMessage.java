package com.ncr.ATMMonitoring.serverchain.message.wrapper;

import com.ncr.ATMMonitoring.serverchain.message.wrapper.visitor.WrapperVisitor;



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
