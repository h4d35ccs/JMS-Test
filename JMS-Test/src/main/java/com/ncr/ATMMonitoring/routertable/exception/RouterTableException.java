package com.ncr.ATMMonitoring.routertable.exception;

/**
 * Indicate an erro while loading the router table
 * @author Otto Abreu
 *
 */
public class RouterTableException extends RuntimeException {

  
    private static final long serialVersionUID = 1L;
    /**
     * LOAD_FILE_EXCEPTION = "An error occurs while loading the table";
     */
    public static final String LOAD_FILE_EXCEPTION = "An error occurs while loading the table";


    /**
     * @param message
     */
    public RouterTableException(String message) {
	super(message);
	// TODO Auto-generated constructor stub
    }

    
    /**
     * @param message
     * @param cause
     */
    public RouterTableException(String message, Throwable cause) {
	super(message, cause);
	// TODO Auto-generated constructor stub
    }



}
