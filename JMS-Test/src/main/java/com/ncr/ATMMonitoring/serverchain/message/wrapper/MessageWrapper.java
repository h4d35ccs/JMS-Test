package com.ncr.ATMMonitoring.serverchain.message.wrapper;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Otto Abreu
 * 
 */
public abstract class MessageWrapper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5407451191989252146L;

    private String message;
    private int id;
    private Date generatedDate;
    private String stamp;
    
    public MessageWrapper (){
	
	this.generatedDate = new Date();
    }
 

    public MessageWrapper(String message, int id) {
	super();
	this.message = message;
	this.id = id;
    }

    public String getMessage() {
	return message;
    }

    public int getId() {
	return id;
    }

    public Date getGeneratedDate() {
	return generatedDate;
    }
    
    public void stampMessage(String serverIp, Date whenMessageHasArrived){
	
	long secondsSinceSend = this.calculateTimeSinceSend(whenMessageHasArrived);
	String newStamp = serverIp+"["+secondsSinceSend+" seconds]";
	this.stamp += " "+newStamp;
	
    }
    
    private long calculateTimeSinceSend(Date whenMessageHasPass){
	
	long sendTime = this.generatedDate.getTime();
	long actual = whenMessageHasPass.getTime();
	long diference = (actual - sendTime);
	long timeInSeconds = diference/100;
	return timeInSeconds;
	
    }

    public String getStamp() {
        return stamp;
    }

    @Override
    public String toString() {
	StringBuffer objectInString = new StringBuffer("[");
	objectInString.append("msg id: " + this.id);
	objectInString.append(",msg: " + this.message);
	objectInString.append(",msg gen date: " + this.generatedDate);
	objectInString.append(",Stamp: " + this.stamp);
	objectInString.append("]");
	return objectInString.toString();
    }

}
