package com.ncr.ATMMonitoring.serverchain.adapter.exception;

/**
 * @author Otto Abreu
 * 
 */
public class ATMUpdateRequestException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * NO_RESPONSE_ERROR = "We received no response from IP: ";
     */
    public static final String NO_RESPONSE_ERROR = "We received no response from IP: ";
    /**
     * UNKNOWN_HOST_ERROR = "Don't know about host: ";
     * 
     */
    public static final String UNKNOWN_HOST_ERROR = "Don't know about host: ";
    /**
     * GENERAL_ERROR_WHILE_REQUESTING_DATA =
     * "An exception was thrown while requesting data from IP:";
     */
    public static final String GENERAL_ERROR_WHILE_REQUESTING_DATA = "An exception was thrown while requesting data from IP:";

    /**
     * SETUP_NOT_EXECUTED =
     * "the update info and the socket coMmunication params are null, please execute the setup method in ATMUpdateRequestAdapterImpl"
     * ;
     */
    public static final String SETUP_NOT_EXECUTED = "the update info and the socket communication params are null, please execute the setup method in ATMUpdateRequestAdapterImpl";

    public ATMUpdateRequestException(String message, Throwable cause) {
	super(message, cause);
	// TODO Auto-generated constructor stub
    }

    public ATMUpdateRequestException(String message) {
	super(message);
	// TODO Auto-generated constructor stub
    }

}
