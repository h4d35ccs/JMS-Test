package com.ncr.ATMMonitoring.serverchain.topicactor.producer;

import java.io.Serializable;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageStamp;

/**
 * @author Otto Abreu
 *
 */
@Component("incomingMessageProducer")
public class IncomingMessageProducer extends GenericMessageProducer {

   
    @Autowired
    private ActiveMQConnectionFactory localConnectionFactory;
    /* (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.topicactor.producer.GenericProducer#getTopicName()
     */
    @Override
    protected String getTopicName() {
	
	return this.getIncomingTopicName();
    }

    /* (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.topicactor.producer.GenericProducer#getLocalConnectionFactory()
     */
    @Override
    protected ActiveMQConnectionFactory getLocalConnectionFactory() {
	
	return this.localConnectionFactory;
    }

    @Override
    public void setMessageStamp(Serializable message) {
	MessageStamp messageStamp = new MessageStamp();
	messageStamp.setSendStampToMessageWraper(message, this.getNodeLocalAddress());
	
    }

}
