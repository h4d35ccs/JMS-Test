package com.ncr.ATMMonitoring.serverchain.topicactor.producer;

import java.io.Serializable;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.message.Stamper;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.MessageStamp;

@Component("outgoingMessageProducer")
public class OutgoingMessageProducer  extends GenericMessageProducer implements Stamper  {

    @Autowired
    private ActiveMQConnectionFactory localConnectionFactory;

    @Override
    protected String getTopicName() {
	
	return this.getOutgoingTopicName();
    }

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
