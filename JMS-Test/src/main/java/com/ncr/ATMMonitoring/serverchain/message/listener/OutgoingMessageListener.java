package com.ncr.ATMMonitoring.serverchain.message.listener;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.processor.MessageProcessor;

@Component
public class OutgoingMessageListener extends  GenericListener {

    private static final Logger logger = Logger
	    .getLogger(OutgoingMessageListener.class);

    @Resource(name="outgoingMessageProcessor")
    private MessageProcessor messageProcessor;

    @Override
    protected MessageProcessor getMessageProcessor() {
	
	return this.messageProcessor;
    }

   
}
