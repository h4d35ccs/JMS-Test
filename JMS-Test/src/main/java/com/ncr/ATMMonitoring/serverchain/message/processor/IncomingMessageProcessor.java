/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.message.processor;

import javax.jms.Message;

import org.springframework.stereotype.Component;

/**
 * @author Otto Abreu
 *
 */
@Component("incomingMessageProcessor")
public class IncomingMessageProcessor implements MessageProcessor{

    @Override
    public void processReceivedMessage(Message message) {
	
	
    }

}
