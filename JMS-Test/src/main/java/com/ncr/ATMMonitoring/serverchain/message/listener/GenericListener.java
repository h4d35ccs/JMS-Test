/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.message.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import com.ncr.ATMMonitoring.serverchain.message.processor.MessageProcessor;

/**
 * @author Otto Abreu
 *
 */
public abstract class GenericListener implements MessageListener {

    private static final Logger logger = Logger
	    .getLogger(GenericListener.class);
    
    @Override
    public void onMessage(Message message) {
	try {

		this.processReceivedMessage(message);

	} catch (JMSException e) {
	    logger.error("Can not process the message due an exception: ", e);
	}

    }

    private void processReceivedMessage(Message message)
	    throws JMSException {

	
	logger.debug("Received Message in consumer " + message);

	this.getMessageProcessor().processReceivedMessage(message);
    }
    
    
    protected abstract MessageProcessor getMessageProcessor();

}
