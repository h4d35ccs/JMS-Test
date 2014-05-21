package com.ncr.serverchain.message.wrapper;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Class in charge of generating an stamp to a wrapper
 * @author Otto Abreu
 * 
 */
public class MessageStamp {

    private Date generatedDate;
    private String allStamps = "";;

    private static final Logger logger = Logger.getLogger(MessageStamp.class);

    private static final String STAMP_SEPARATOR = "--";
    /**
     * set the exit stamp to the given message
     * @param message
     * @param nodeLocalAddress
     */
    public void setSendStampToMessageWraper(Serializable message,
	    String nodeLocalAddress) {

	MessageWrapper msgWrapper = this.extractInfoFromMessageWrapper(message,
		nodeLocalAddress);
	String stampUpdated = this.sendStamp(nodeLocalAddress);
	msgWrapper.setStamp(stampUpdated);

    }

    private MessageWrapper extractInfoFromMessageWrapper(Serializable message,
	    String nodeLocalAddress) {

	verifyIfMessageIsAWrapper(message);

	MessageWrapper msgWrapper = (MessageWrapper) message;
	this.generatedDate = msgWrapper.getGeneratedDate();
	this.allStamps = msgWrapper.getStamp();

	return msgWrapper;
    }

    private void verifyIfMessageIsAWrapper(Serializable message) {

	if (!(message instanceof MessageWrapper)) {
	    logger.error("Wrong type of message, should be MessageWrapper, received:"
		    + message.getClass());
	    throw new IllegalArgumentException(
		    "Wrong type of message, should be MessageWrapper, received:"
			    + message.getClass());
	}
    }

    private String sendStamp(String serverIp) {
	
	double secondsSinceSend = this.calculateTimeSinceSentOrReceived(
		this.generatedDate, new Date());
	return this.stampMessage(serverIp, secondsSinceSend, "Sent at");
    }
    /**
     * set the received stamp to the given message
     * @param message
     * @param nodeLocalAddress
     */
    public void setReceivedStampToMessageWraper(Serializable message,
	    String nodeLocalAddress) {
	
	MessageWrapper msgWrapper = this.extractInfoFromMessageWrapper(message,
		nodeLocalAddress);
	String stampUpdated = this.receivedStamp(nodeLocalAddress);
	msgWrapper.setStamp(stampUpdated);

    }

    private String receivedStamp(String serverIp) {
	
	double secondsSinceSend = this.calculateTimeSinceSentOrReceived(
		this.generatedDate, new Date());
	return this.stampMessage(serverIp, secondsSinceSend, "Received at");
    }

    private String stampMessage(String serverIp, double secWhenReceivedOrSent,
	    String sendOrReceivedMessage) {

	if (StringUtils.countMatches(allStamps, STAMP_SEPARATOR) >= 1) {

	    String newStamp = serverIp + "[" + sendOrReceivedMessage + " : "
		    + secWhenReceivedOrSent + " seconds]";
	    this.allStamps +=  newStamp + STAMP_SEPARATOR;

	} else {

	    this.allStamps += STAMP_SEPARATOR;
	}
	return allStamps;

    }

    private double calculateTimeSinceSentOrReceived(Date generatedDate,
	    Date whenMessageHasPass) {

	long sendTime = generatedDate.getTime();
	long actual = whenMessageHasPass.getTime();
	double diference = (actual - sendTime);
	double timeInSeconds = diference / 100;
	return timeInSeconds;
    }
}
