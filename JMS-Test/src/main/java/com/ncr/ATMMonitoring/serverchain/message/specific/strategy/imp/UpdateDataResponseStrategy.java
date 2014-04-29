package com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp;

import com.ncr.ATMMonitoring.serverchain.NodePosition;
import com.ncr.ATMMonitoring.serverchain.message.SpecificMessage;
import com.ncr.ATMMonitoring.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy;

/**
 * @author Otto Abreu
 *
 */
public class UpdateDataResponseStrategy implements
	SpecifcMessageProcessStrategy {

 

    /* (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#setupStrategy(com.ncr.ATMMonitoring.serverchain.NodePosition, com.ncr.ATMMonitoring.serverchain.message.SpecificMessage)
     */
    @Override
    public void setupStrategy(NodePosition postion, SpecificMessage message) {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#canProcessSpecificMessage()
     */
    @Override
    public boolean canProcessSpecificMessage() {
	// TODO Auto-generated method stub
	return true;
    }

    /* (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#processSpecificMessage()
     */
    @Override
    public void processSpecificMessage() {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#passToOtherNode()
     */
    @Override
    public boolean passToOtherNode() {
	// TODO Auto-generated method stub
	return false;
    }

}
