package com.ncr.ATMMonitoring.serverchain.message.listener;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.processor.MessageProcessor;

@Component
public class OutgoingMessageListener extends  GenericListener {

//    private static final Logger logger = Logger
//	    .getLogger(OutgoingMessageListener.class);

    @Resource(name="outgoingMessageProcessor")
    private MessageProcessor messageProcessor;

    @Override
    protected MessageProcessor getMessageProcessor() {
	
	return this.messageProcessor;
    }

   
}
