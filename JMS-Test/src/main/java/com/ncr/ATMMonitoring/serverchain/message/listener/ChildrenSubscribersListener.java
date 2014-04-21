/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.message.listener;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.CommandTypes;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.DataStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.processor.ChildrenSubscribersMessageProccessor;
import com.ncr.ATMMonitoring.serverchain.message.processor.MessageProcessor;

/**
 * @author Otto Abreu
 * 
 */
@Component
public class ChildrenSubscribersListener implements MessageListener {

    @Resource(name="childrenSubscribersMessageProccessor")
    
    private MessageProcessor messageProccessor;
    
    public void onMessage(Message message) {
	
	this.messageProccessor.processReceivedMessage(message);
    }
}
