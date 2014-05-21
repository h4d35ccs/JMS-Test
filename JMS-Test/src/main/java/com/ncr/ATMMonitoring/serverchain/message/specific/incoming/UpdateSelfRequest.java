package com.ncr.ATMMonitoring.serverchain.message.specific.incoming;

import com.ncr.serverchain.message.specific.SpecificMessage;

/**
 * Holds the information regarding an ATM asking for an update
 * @author Otto Abreu
 * 
 */
public class UpdateSelfRequest implements SpecificMessage {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String atmIp;

    private int matricula;
    
    
    public UpdateSelfRequest(String atmIp, int matricula) {
	super();
	this.atmIp = atmIp;
	this.matricula = matricula;
    }

    public String getAtmIp() {
        return atmIp;
    }

    public int getMatricula() {
        return matricula;
    }
    
    

}
