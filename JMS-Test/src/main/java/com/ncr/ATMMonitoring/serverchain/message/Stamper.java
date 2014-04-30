package com.ncr.ATMMonitoring.serverchain.message;

import java.io.Serializable;

/**
 * Define the behavior that allows a class to print a mark (stamp) in a message
 * 
 * @author Otto Abreu
 * 
 */
public interface Stamper {
    /**
     * Set the stamp into the given message
     * @param message
     */
    void setMessageStamp(Serializable message);
}
