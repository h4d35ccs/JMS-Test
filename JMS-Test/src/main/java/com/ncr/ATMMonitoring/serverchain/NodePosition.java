package com.ncr.ATMMonitoring.serverchain;

/**
 * Define the possible positions that a node could have
 * @author Otto Abreu
 *
 */
public enum NodePosition {
    
    FIRST_NODE{
	@Override
	public String toString(){
	    return "First node";
	}
    }, 
    LEAF_NODE{
	
	@Override
	public String toString(){
	    return "Leaf node";
	}
	
    }, 
    MIDDLE_NODE{

	@Override
	public String toString() {
	    return "Middle node";
	}
	
    }, 
    ONLY_NODE{

	@Override
	public String toString() {
	   
	    return "Only Node";
	}
	
    };
    
    
    @Override
    public abstract String toString();

}
