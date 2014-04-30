package com.ncr.ATMMonitoring.serverchain.message.listener;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.processor.MessageProcessor;

/**
 * <pre>
 * Concrete class from GenericListener that sets the MessageProcessor in charge of 
 * processing the related to the outgoing messages
 * 
 * @author Otto Abreu
 * 
 * </pre>
 * 
 */
@Component
public class OutgoingMessageListener extends GenericListener {

    @Resource(name = "outgoingMessageProcessor")
    private MessageProcessor messageProcessor;

    @Override
    protected MessageProcessor getMessageProcessor() {

	return this.messageProcessor;
    }

}
