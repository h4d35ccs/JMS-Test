package com.ncr.ATMMonitoring.serverchain.message.specific.incoming;

/**
 * Holds the request for a new Matricula for an ATM
 * @author Otto Abreu
 *
 */
public class MatriculaRequest {
    
    
    
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
