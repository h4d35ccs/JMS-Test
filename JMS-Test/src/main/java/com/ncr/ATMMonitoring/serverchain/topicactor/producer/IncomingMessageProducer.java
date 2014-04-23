package com.ncr.ATMMonitoring.serverchain.topicactor.producer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

}
