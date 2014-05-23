package com.ncr.serverchain;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Class that construct the main objects of  serverchain ( NodeInformation and MessagePublisher)
 * 
 * This class should be used in those environments where is not possible to get
 * the NodeInformation or MessagePublisher class using the autowired attribute of spring
 * 
 * @author Otto Abreu
 * 
 */
public class ServerchainMainBeanFactory {

    /**
     * <pre>
     * Returns the NodeInformation object using the ApplicationContext obtained from the given
     * spring config files.
     * this method uses ClassPathXmlApplicationContext in order to get the ApplicationContext
     * @param configFiles String[] with valid Spring xml present in the classpath
     * </pre>
     * 
     * @return NodeInformation
     */
    public static NodeInformation getNodeInformationInstance(String[] springConfigFiles) {

	ApplicationContext context = getSpringApplicationContext(springConfigFiles);
	NodeInformation nodeInfo = context.getBean(NodeInformation.class);
	return nodeInfo;
    }

    /**
     * <pre>
     * Returns the NodeInformation object using the ApplicationContext obtained from the default
     * spring config file: serverchain-config.xm
     * this method uses ClassPathXmlApplicationContext in order to get the ApplicationContext
     * </pre>
     * 
     * @return NodeInformation
     */
    public static NodeInformation getNodeInformationInstance() {

	ApplicationContext context = getDefaultSpringApplicationContext();
	NodeInformation nodeInfo = context.getBean(NodeInformation.class);
	return nodeInfo;
    }
    
    /**
     * <pre>
     * Returns the MessagePublisher object using the ApplicationContext obtained from the given
     * spring config files.
     * this method uses ClassPathXmlApplicationContext in order to get the ApplicationContext
     * @param configFiles String[] with valid Spring xml present in the classpath
     * </pre>
     * 
     * @return NodeInformation
     */
    public static MessagePublisher getMessagePublisherInstance(String[] springConfigFiles) {

	ApplicationContext context = getSpringApplicationContext(springConfigFiles);
	MessagePublisher nodeInfo = context.getBean(MessagePublisher.class);
	return nodeInfo;
    }

    
    /**
     * <pre>
    * Returns the MessagePublisher object using the ApplicationContext obtained from the default
    * spring config file: serverchain-config.xm
    * this method uses ClassPathXmlApplicationContext in order to get the ApplicationContext
    * </pre>
     * @return MessagePublisher
     */
    public static MessagePublisher getMessagePublisherInstance(){
	ApplicationContext context = getDefaultSpringApplicationContext();
	MessagePublisher messagePublisher = context.getBean(MessagePublisher.class);
	return messagePublisher;
    }
    
    
    
    private static ApplicationContext  getDefaultSpringApplicationContext(){
	ApplicationContext context = getSpringApplicationContext(new String[] { "serverchain-config.xml" });
	return context;
    }
    

    private static ApplicationContext getSpringApplicationContext(
	    String[] configFiles) {

	ApplicationContext context = new ClassPathXmlApplicationContext(
		configFiles);

	return context;

    }

}
