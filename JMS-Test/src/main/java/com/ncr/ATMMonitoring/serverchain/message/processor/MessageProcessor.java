/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.message.processor;

import javax.jms.Message;

/**
 * @author Otto Abreu
 * 
 */
public interface MessageProcessor {

    void processReceivedMessage(Message message);
}
