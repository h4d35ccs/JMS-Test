package com.ncr.ATMMonitoring.serverchain.message.specific.strategy;


/**
 * constants to define the possible broadcast type
 * @author Otto Abreu
 *
 */
public enum BroadcastType {
    NONE {
	    @Override
	    public String toString() {
		
		return "NONE";
	    }
	},ONE_WAY{
	   
	    @Override
	    public String toString() {
		
		return "ONE WAY";
	    }
	}, TWO_WAY{
	    
	    @Override
	    public String toString() {
		
		return "TWO WAY";
	    }
	};
	
	public abstract String toString();
}
