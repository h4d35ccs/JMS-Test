package com.ncr.ATMMonitoring.serverchain.message;

import java.util.Date;

public class OutgoingMessage extends MessageWrapper {

    
    private static final long serialVersionUID = 1L;
    
    public OutgoingMessage(String message, int id, Date generatedDate) {
	super(message, id, generatedDate);
	
    }


    

}
