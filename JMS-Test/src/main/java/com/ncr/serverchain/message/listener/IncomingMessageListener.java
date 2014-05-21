/**
 * 
 */
package com.ncr.serverchain.message.listener;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ncr.serverchain.message.processor.MessageProcessor;

/**
 * <pre>
 * Concrete class from GenericListener that sets the MessageProcessor in charge of 
 * processing the related to the incoming messages
 * @author Otto Abreu
 * 
 * </pre>
 * 
 */
@Component
public class IncomingMessageListener extends GenericListener {

    @Resource(name = "incomingMessageProcessor")
    private MessageProcessor messageProcessor;

    @Override
    protected MessageProcessor getMessageProcessor() {
	return this.messageProcessor;
    }

}
