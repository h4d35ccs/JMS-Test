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
    };

    public abstract String toString();
}
