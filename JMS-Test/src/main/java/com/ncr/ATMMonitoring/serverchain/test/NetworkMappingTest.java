package com.ncr.ATMMonitoring.serverchain.test;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.serverchain.NodeInformation;
import com.ncr.serverchain.NodePosition;
import com.ncr.serverchain.networkmap.NetworkMapHandler;

/**
 * @author Otto Abreu
 *
 */
@Component
public class NetworkMappingTest {
    
    @Autowired
    private NetworkMapHandler networkMapHandler;
    
    @Autowired
    private NodeInformation nodePosition;

    private static final Logger logger = Logger
	    .getLogger(NetworkMappingTest.class);
    
    @Scheduled(fixedDelay = 10000)
    public void getMap(){
	
	if(nodePosition.getNodePosition().equals(NodePosition.FIRST_NODE)){ 
        	logger.info("networkMap: "+this.networkMapHandler.getNetworkMap().toString());
	}
    }

}
