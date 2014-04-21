/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.executer;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.topicactor.consumer.TopicConsumer;

/**
 * @author Otto Abreu
 *
 */
@Component
public class SubscribersConsumerExecuter {

    @Resource(name="subscribersTopicConsumer")
    private TopicConsumer consumer;
    

    private static final Logger logger = Logger
	    .getLogger(SubscribersConsumerExecuter.class);

    @Scheduled(cron = "1 * * * * *")
    public void checkConsumers() throws Exception {
	logger.debug("checking the subscriber topic ");
	this.consumer.setup();
	this.consumer.consumeMessage();
    }
}
