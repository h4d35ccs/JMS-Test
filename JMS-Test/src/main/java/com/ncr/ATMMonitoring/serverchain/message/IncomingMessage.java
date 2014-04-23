/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain.message;

import java.util.Date;

/**
 * @author Otto Abreu
 *
 */
public class IncomingMessage extends MessageWrapper{

    public IncomingMessage(String message, int id, Date generatedDate) {
	super(message, id, generatedDate);
	
    }

    /**
     * 
     */
    private static final long serialVersionUID = -6457607990812054891L;

    
    
}
