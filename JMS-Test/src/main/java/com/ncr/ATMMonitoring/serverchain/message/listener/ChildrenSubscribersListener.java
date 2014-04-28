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
public class ChildrenSubscribersListener extends GenericListener {

    @Resource(name = "childrenSubscribersMessageProccessor")
    private MessageProcessor messageProccessor;

    @Override
    protected MessageProcessor getMessageProcessor() {
	
	return this.messageProccessor;
    }

}
