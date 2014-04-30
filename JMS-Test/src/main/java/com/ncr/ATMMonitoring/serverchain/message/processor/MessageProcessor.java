package com.ncr.ATMMonitoring.serverchain.message.processor;

import javax.jms.Message;

/**
 * <pre>
 * The message processor is in charge of applying logic to a JMS Message.
 * 
 * @author Otto Abreu
 * </pre>
 */
public abstract class MessageProcessor {

    public abstract void processReceivedMessage(Message message);

    /**
     * Generic method that generates an exception for cases where the
     * ObjectMessage is not an instance of MessageWrapper
     * 
     * @param message
     * @param wrongClassObject
     * @return IllegalArgumentException
     */
    protected IllegalArgumentException generateArgumentExeption(String message,
	    Object wrongClassObject) {
	throw new IllegalArgumentException(
		"the message inside the ObjectMessage  should be instance of  MessageWrapper, received: "

			+ wrongClassObject.getClass());
    }
}
