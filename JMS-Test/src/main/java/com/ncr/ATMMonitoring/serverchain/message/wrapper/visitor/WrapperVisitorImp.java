package com.ncr.ATMMonitoring.serverchain.message.wrapper.visitor;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.ChainLinkInformation;
import com.ncr.ATMMonitoring.serverchain.message.SpecificMessage;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.IncomingMessage;
import com.ncr.ATMMonitoring.serverchain.message.wrapper.OutgoingMessage;
import com.ncr.ATMMonitoring.serverchain.topicactor.producer.GenericMessageProducer;

/**
 * @author Otto Abreu
 * 
 */
@Component
public class WrapperVisitorImp implements WrapperVisitor {

   
    @Resource(name="outgoingMessageProducer")
    private GenericMessageProducer outgoingProducer;
   
    @Resource(name="incomingMessageProducer")
    private GenericMessageProducer incomingProducer;

    @Autowired
    private ChainLinkInformation chainLinkInformation;

    private static final Logger logger = Logger
	    .getLogger(WrapperVisitorImp.class);

    @Override
    public void visit(IncomingMessage message) {
	logger.debug("visiting incoming Message");
	if(this.passIncomingMessageToParentNode()){
	    this.incomingProducer.sendMessage(message);
	}
    }

    @Override
    public void visit(OutgoingMessage message) {
	SpecificMessage specificMessage = message.getSpecificMessage();
	logger.debug("visiting outgoing Message");
	if (this.passOutgoingMessageToNextNode(specificMessage)) {

	    this.outgoingProducer.sendMessage(message);

	} else if (this.isFinalProccessingNode()) {

	}

    }

    private boolean passOutgoingMessageToNextNode(
	    SpecificMessage specificMessage) {
	boolean passMessage = false;

	if (this.chainLinkInformation.isMiddleNode()
		&& this.canProcessMessage(specificMessage)) {
	    passMessage = true;
	}

	return passMessage;
    }

    private boolean passIncomingMessageToParentNode() {
	boolean passMessage = true;
	if (this.chainLinkInformation.isFirstNode()) {
	    passMessage = false;
	}

	return passMessage;
    }

    private boolean canProcessMessage(SpecificMessage specificMessage) {
	return false;
    }

    private boolean isFinalProccessingNode() {
	return this.chainLinkInformation.isLeaf();
    }

}
