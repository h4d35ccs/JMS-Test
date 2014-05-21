package com.ncr.serverchain.message.specific.strategy.imp;

import org.springframework.context.ApplicationContext;

import com.ncr.serverchain.NodeInformation;
import com.ncr.serverchain.NodePosition;
import com.ncr.serverchain.message.specific.SpecificMessage;
import com.ncr.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy;
import com.ncr.serverchain.message.wrapper.MessageWrapper;

/**
 * @author Otto Abreu
 *
 */
public abstract class BaseStrategy implements SpecifcMessageProcessStrategy {

    protected NodeInformation nodeInformation;
    protected SpecificMessage messageToProcess;
    protected ApplicationContext springContext;

    
    /* (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#setupStrategy(com.ncr.ATMMonitoring.serverchain.NodeInformation, com.ncr.ATMMonitoring.serverchain.message.specific.SpecificMessage)
     */
    @Override
    public void setupStrategy(
	    SpecificMessage message, ApplicationContext springContext) {

	this.messageToProcess = message;
	this.springContext = springContext;
	this.nodeInformation = (NodeInformation)this.getSpringBean(NodeInformation.class);

    }
    
    protected boolean isLeaf(){
	
   	if(this.nodeInformation.getNodePosition()
   	.equals(NodePosition.LEAF_NODE)){
   	    
   	    return true;
   	    
   	}else{
   	    
   	    return false;
   	}
       }

    protected boolean isRoot() {

   	if (this.nodeInformation.getNodePosition().equals(
   		NodePosition.FIRST_NODE)) {

   	    return true;

   	} else {

   	    return false;
   	}
       }
       
    protected boolean isMiddle(){
   	
   	if (this.nodeInformation.getNodePosition()
   		.equals(NodePosition.MIDDLE_NODE)){
   	    return true;
   	}else{
   	    return false;
   	}
     }
    
    /*
     * (non-Javadoc)
     * @see com.ncr.ATMMonitoring.serverchain.message.specific.strategy.SpecifcMessageProcessStrategy#getTurnBackdMessage()
     */
    @Override
    public MessageWrapper getTurnBackMessage() {
	
	return null;
    }
    /**
     * Returns a spring bean from the given Application context
     * @param springbeanClass
     * @return Object
     */
    protected Object getSpringBean(Class<?> springbeanClass){
	return this.springContext
	.getBean(springbeanClass);
    }

}
