/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.message.listener;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.processor.MessageProcessor;

/**
 * @author Otto Abreu
 * 
 */
@Component
public class IncomingMessageListener extends GenericListener {

    @Resource(name = "incomingMessageProcessor")
    private MessageProcessor messageProcessor;

    @Override
    protected MessageProcessor getMessageProcessor() {
	// TODO Auto-generated method stub
	return this.messageProcessor;
    }

}
