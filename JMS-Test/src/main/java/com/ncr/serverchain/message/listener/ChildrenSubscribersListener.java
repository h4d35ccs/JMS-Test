/**
 * 
 */
package com.ncr.serverchain.message.listener;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ncr.serverchain.message.processor.MessageProcessor;

/**
 * <pre>
 * Concrete class from GenericListener that sets the MessageProcessor in charge of 
 * processing the related to the children subscribers
 * @author Otto Abreu
 * 
 * </pre>
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
