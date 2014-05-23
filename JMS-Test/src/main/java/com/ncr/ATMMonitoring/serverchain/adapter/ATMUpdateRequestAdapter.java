package com.ncr.ATMMonitoring.serverchain.adapter;

import com.ncr.ATMMonitoring.updatequeue.ATMUpdateInfo;

/**
 * @author Otto Abreu
 *
 */
public interface ATMUpdateRequestAdapter {
    
    
    /**
     * Setup that prepares the adapter
     * @param updateInfo
     * @param socketComunicationParams
     */
    void setupAdapter(ATMUpdateInfo updateInfo,ATMSocketComunicationParams socketComunicationParams);
    
    /**
     * Executes the request of the given ATM.
     * This method will call directly the ATM or initiate the 
     * serverchain process.
     * 
     * The setup method should be called before executing this method
     */
    void requestATMUpdate();

}
