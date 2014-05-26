package com.ncr.ATMMonitoring.serverchain.message.specific.strategy.imp;



import org.apache.log4j.Logger;

import com.ncr.ATMMonitoring.serverchain.message.specific.incoming.UpdateRequestCommunicationError;
import com.ncr.ATMMonitoring.socket.SocketService;
import com.ncr.ATMMonitoring.updatequeue.ATMUpdateInfo;
import com.ncr.serverchain.message.specific.strategy.BroadcastType;
import com.ncr.serverchain.message.specific.strategy.imp.BaseStrategy;

/**
 * @author Otto Abreu
 *
 */
public class UpdateRequestCommunicationErrorStrategy extends BaseStrategy {

    
    private static final Logger logger = Logger
	    .getLogger(UpdateRequestCommunicationErrorStrategy.class);
    @Override
    public boolean canProcessSpecificMessage() {
	//to make sure that the message reaches the ROOT
	return true;
    }

    @Override
    public void processSpecificMessage() {
	if(this.isRoot()){
	   
	    SocketService socketService = this.springContext.getBean(SocketService.class);
	    ATMUpdateInfo updateInfo  = this.getUpdateInfo();
	    socketService.updateTerminalSocket(updateInfo);
	    
	    logger.info("Added ATM not responding info to the update Queue in root: "+updateInfo);
	}
	
    }
    
    
    private ATMUpdateInfo getUpdateInfo(){
	UpdateRequestCommunicationError updateError = (UpdateRequestCommunicationError)this.messageToProcess;
	String atmIp = updateError.getAtmIp();
	Long atmMatricula = updateError.getMatricula();
	ATMUpdateInfo updateInfo  = new ATMUpdateInfo(atmIp, atmMatricula);
	return updateInfo;
    }

    @Override
    public BroadcastType broadcastDirection() {
	//the message only have to travel in one direction until
	//it reaches ROOT
	return BroadcastType.ONE_WAY;
    }

}
