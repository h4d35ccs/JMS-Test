package com.ncr.ATMMonitoring.serverchain.message.specific.incoming;

import com.ncr.serverchain.message.specific.SpecificMessage;

/**
 * Holds the request for a new Matricula for an ATM
 * @author Otto Abreu
 *
 */
public class MatriculaRequest implements SpecificMessage {
    
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String requestedFromURL;
    
    private String atmIptoSetMatricula;

    public MatriculaRequest(String requestedFromURL, String atmIptoSetMatricula) {
	super();
	this.requestedFromURL = requestedFromURL;
	this.atmIptoSetMatricula = atmIptoSetMatricula;
    }

    public String getRequestedFromURL() {
        return requestedFromURL;
    }

    public String getAtmIptoSetMatricula() {
        return atmIptoSetMatricula;
    }
    
    

}
