package com.ncr.serverchain.message.wrapper;

import java.io.Serializable;
import java.util.Date;

import com.ncr.serverchain.message.specific.SpecificMessage;
import com.ncr.serverchain.message.wrapper.visitor.VisitableMessageWrapper;

/**
 * <pre>
 * The wrapper is a class that holds the real messages that travels between nodes.
 * Acts like a envelop.
 * 
 * @author Otto Abreu
 * </pre>
 */
public abstract class MessageWrapper implements Serializable, VisitableMessageWrapper {

    
    public static final String DEFAULT_OUTGOINGMESSAGE_INNER_MESSAGE="Outgoing message to send from: ";
    /**
     * DEFAULT_INCOMINGMESSAGE_INNER_MESSAGE="Incoming message to send from: ";

     */
    public static final String DEFAULT_INCOMINGMESSAGE_INNER_MESSAGE="Incoming message to send from: ";
    /**
     * 
     */
    private static final long serialVersionUID = -5407451191989252146L;

    private String message;
    private int id;
    private Date generatedDate;
    private String stamp = "";
    private SpecificMessage specificMessage;

    public MessageWrapper() {

	this.generatedDate = new Date();
    }

    public MessageWrapper(String message, int id) {
	super();
	this.message = message;
	this.id = id;
	this.generatedDate = new Date();
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

    public String getStamp() {
	return stamp;
    }

    public void setStamp(String stamp) {
	this.stamp = stamp;
    }
    
    public SpecificMessage getSpecificMessage() {
        return specificMessage;
    }

    public void setSpecificMessage(SpecificMessage specificMessage) {
        this.specificMessage = specificMessage;
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
