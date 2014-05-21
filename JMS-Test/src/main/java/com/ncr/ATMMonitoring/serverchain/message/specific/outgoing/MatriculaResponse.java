package com.ncr.ATMMonitoring.serverchain.message.specific.outgoing;

import com.ncr.ATMMonitoring.serverchain.message.specific.incoming.MatriculaRequest;
import com.ncr.serverchain.message.specific.SpecificMessage;

/**
 * Holds the new matricula to set in an ATM
 * @author Otto Abreu
 *
 */
public class MatriculaResponse implements SpecificMessage {
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int newMatricula;
    
    private MatriculaRequest originalRequest;
    
    public MatriculaResponse( int newMatricula, MatriculaRequest originalRequest) {
	super();
	this.newMatricula = newMatricula;
	this.originalRequest = originalRequest;
    }

    public String getRequestedFromURL() {
        return originalRequest.getRequestedFromURL();
    }

    public int getNewMatricula() {
        return newMatricula;
    }

    public String getAtmIptoSetMatricula() {
        return originalRequest.getAtmIptoSetMatricula();
    }

}
