/**
 * 
 */
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
@Component("incomingMessageProcessor")
public class IncomingMessageProcessor extends MessageWrapperProcessor implements Stamper {

    private static final Logger logger = Logger
	    .getLogger(IncomingMessageProcessor.class);
    
    @Autowired
    private WrapperVisitor visitor; 

    @Override
    protected void processMessage(MessageWrapper message) {
	this.setMessageStamp(message);
	logger.debug("received message in Incoming processor:" + message);
	message.accept(this.visitor);

    }

    
    @Override
    public void setMessageStamp(Serializable message) {
	MessageStamp messageStamp = new MessageStamp();
	messageStamp.setReceivedStampToMessageWraper(message, this.getLocalBrokerURL());
	
    }
}
