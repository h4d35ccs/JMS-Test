package com.ncr.ATMMonitoring.serverchain.message;

import java.io.Serializable;
import java.util.Date;

public class OutgoingMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
    private int id;
    private Date generatedDate;

    public OutgoingMessage(String message, int id, Date generatedDate) {
	super();
	this.message = message;
	this.id = id;
	this.generatedDate = generatedDate;
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

    @Override
    public String toString() {
	StringBuffer objectInString = new StringBuffer("[");
	objectInString.append("msg id: " + this.id);
	objectInString.append(",msg: " + this.message);
	objectInString.append(",msg gen date: " + this.generatedDate);
	objectInString.append("]");
	return objectInString.toString();
    }

}
