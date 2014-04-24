/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.message.processor;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageWrapper;

/**
 * @author Otto Abreu
 * 
 */
@Component("incomingMessageProcessor")
public class IncomingMessageProcessor extends ObjectMessageProcessor {

    private static final Logger logger = Logger
	    .getLogger(IncomingMessageProcessor.class);

    @Override
    protected void processMessage(MessageWrapper message) {
	logger.debug("received message in Incoming processor:" + message);

    }

}
