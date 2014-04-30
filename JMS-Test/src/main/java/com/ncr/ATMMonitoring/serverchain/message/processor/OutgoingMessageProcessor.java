package com.ncr.ATMMonitoring.serverchain.message.processor;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.Stamper;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageStamp;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageWrapper;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.visitor.WrapperVisitor;

/**
 * @author Otto Abreu
 * 
 */
@Component("outgoingMessageProcessor")
public class OutgoingMessageProcessor extends MessageWrapperProcessor implements Stamper {

    @Autowired
    private WrapperVisitor visitor;
    
    private static final Logger logger = Logger
	    .getLogger(OutgoingMessageProcessor.class);

    @Override
    protected void processMessage(MessageWrapper message) {
	
	this.setMessageStamp(message);
	logger.debug("received message in outgoing processor:" + message);
	message.accept(this.visitor);
	
    }

    @Override
    public void setMessageStamp(Serializable message) {
	MessageStamp messageStamp = new MessageStamp();
	messageStamp.setReceivedStampToMessageWraper(message, this.getLocalBrokerURL());
	
    }

}
