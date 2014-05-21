package com.ncr.ATMMonitoring.serverchain.test;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ncr.serverchain.MessagePublisher;
import com.ncr.serverchain.NodeInformation;
import com.ncr.serverchain.NodePosition;
import com.ncr.serverchain.message.specific.outgoing.ChangeLeafNodeRouterTableRequest;

/**
 * @author Otto Abreu
 * 
 */
@Component
public class ChangeRouterTableInLeafTest {

    @Autowired
    private NodeInformation nodePosition;

    @Autowired
    private MessagePublisher messagePublisher;

    private static final Logger logger = Logger
	    .getLogger(NetworkMappingTest.class);

    private static String NODE_ADDRESS = "153.57.97.118:61619";
    private static String NODE_ADDRESS2 = "153.57.97.118:61622";
    private static String NODE_ADDRESS3 = "153.57.97.118:61623";

    private int cont = 0;

    @Scheduled(cron = "*/40 * * * * ?")
    public void sendMessageToNode() {
	logger.debug("change from root cont (>2):" + this.cont);
	String nodeAdrr = getNodeAddressByCont();
	if (nodePosition.getNodePosition().equals(NodePosition.FIRST_NODE)
		&& (this.cont > 2) && (nodeAdrr != null)) {

	    Properties props = getpropertiesByCont();
	    logger.debug("Node Addr:" + nodeAdrr + " props:" + props);
	    ChangeLeafNodeRouterTableRequest request = new ChangeLeafNodeRouterTableRequest(
		    nodeAdrr, props);

	    messagePublisher.publishOutgoingMessage(this.cont,
		    "changing table to:" + nodeAdrr, request);
	    logger.debug(" request to change the router table sent to:"
		    + nodeAdrr);
	}
	this.cont++;
    }

    private String getNodeAddressByCont() {
	String nodeAdrr = null;

	if ((this.cont % 3) == 0) {

	    nodeAdrr = NODE_ADDRESS;

	} else if ((this.cont % 5) == 0) {

	    nodeAdrr = NODE_ADDRESS2;

	} else if ((this.cont % 7) == 0) {

	    nodeAdrr = NODE_ADDRESS3;
	} else if ((this.cont % 13) == 0) {
	    nodeAdrr = NODE_ADDRESS3;
	}

	return nodeAdrr;
    }

    private Properties getpropertiesByCont() {
	Properties props = null;

	if ((this.cont % 3) == 0) {
	    logger.debug("proiperties for first node");
	    props = forFirstNode();

	} else if ((this.cont % 5) == 0) {
	    logger.debug("proiperties for second node");
	    props = forSecondNode();

	} else if ((this.cont % 7) == 0) {
	    logger.debug("proiperties for third node");
	    props = forThirdNode();

	} else if ((this.cont % 13) == 0) {
	    logger.debug("proiperties for third node(alternate)");
	    props = forThirdNodeAlternate();
	}

	return props;
    }

    private static Properties forFirstNode() {
	Properties props = new Properties();
	props.put("3", NODE_ADDRESS);
	props.put("5", NODE_ADDRESS);
	props.put("7", NODE_ADDRESS);
	props.put("8", NODE_ADDRESS);
	return props;
    }

    private static Properties forSecondNode() {
	Properties props = new Properties();
	props.put("3", NODE_ADDRESS2);
	props.put("10", NODE_ADDRESS2);
	props.put("700", NODE_ADDRESS2);
	props.put("878", NODE_ADDRESS2);
	return props;
    }

    private static Properties forThirdNode() {
	Properties props = new Properties();
	props.put("3", NODE_ADDRESS3);
	props.put("500", NODE_ADDRESS3);
	props.put("7899", NODE_ADDRESS3);
	props.put("8000", NODE_ADDRESS3);
	return props;
    }

    private static Properties forThirdNodeAlternate() {
	Properties props = new Properties();
	props.put("965", NODE_ADDRESS3);
	props.put("520", NODE_ADDRESS3);
	props.put("239", NODE_ADDRESS3);
	props.put("830", NODE_ADDRESS3);
	return props;
    }

}
