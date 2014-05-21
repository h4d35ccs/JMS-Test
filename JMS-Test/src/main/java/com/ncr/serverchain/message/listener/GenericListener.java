/**
 * 
 */
package com.ncr.serverchain.message.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;

import com.ncr.serverchain.message.processor.MessageProcessor;

/**
 * <pre>
 * The listener is the class that receive the message from a topic.
 * 
 * The sole responsibility is to receive the message once it arrives to the topic.
 * 
 * The message processing is delegated to a MessageProcessor concrete class. this means that this class implements a Template method pattern,
 *  where the logic related to the message processing is delegated to the concrete classes
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Template_method_pattern">Template method pattern</a>
 * 
 * @author Otto Abreu
 * </pre>
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

    private void processReceivedMessage(Message message) throws JMSException {

	this.getMessageProcessor().processReceivedMessage(message);
    }

 
    protected abstract MessageProcessor getMessageProcessor();

}
