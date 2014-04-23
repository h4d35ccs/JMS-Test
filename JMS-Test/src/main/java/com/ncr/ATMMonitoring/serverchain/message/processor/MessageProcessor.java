package com.ncr.ATMMonitoring.serverchain.message.processor;

import javax.jms.Message;

/**
 * @author Otto Abreu
 *
 */
public abstract class MessageProcessor {

    public abstract void processReceivedMessage(Message message);
    
    
    protected IllegalArgumentException generateArgumentExeption(String message,
	    Object wrongClassObject) {
	throw new IllegalArgumentException(
		"the message inside the ObjectMessage  should be instance of  MessageWrapper, received: "

			+ wrongClassObject.getClass());
    }
}
