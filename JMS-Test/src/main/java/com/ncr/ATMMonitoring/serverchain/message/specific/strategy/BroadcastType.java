package com.ncr.ATMMonitoring.serverchain.message.specific.strategy;

/**
 * constants to define the possible broadcast type
 * 
 * @author Otto Abreu
 * 
 */
public enum BroadcastType {
    /**
     * Means the message will not be broadcasted
     */
    NONE {
	@Override
	public String toString() {

	    return "NONE";
	}

    },
    /**
     * Means the message will be broadcasted in only one direction, from top to
     * bottom or from bottom to top
     */
    ONE_WAY {

	@Override
	public String toString() {

	    return "ONE WAY";
	}

    },
    /**
     * Means the message will be broadcasted in two one direction, from top to
     * bottom and from bottom to top
     */

    TWO_WAY {

	@Override
	public String toString() {

	    return "TWO WAY";
	}

	
    },
    /**
     * Means that the message will turn to the other way, if it was a incoming, will go back as outgoing and viceversa
     */
    TURN_BACK{

	@Override
	public String toString() {
	   
	    return "TURN_BACK";
	}
	    
    };

    public abstract String toString();
}
