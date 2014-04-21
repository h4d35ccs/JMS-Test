/**
 * 
 */
package com.ncr.ATMMonitoring.serverchain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ncr.ATMMonitoring.serverchain.topicactor.TopicActor;

/**
 * @author Otto Abreu
 * 
 */
@Component
public class ChildrenLinkListHandler {

    public Set<String> getChildrenSubscribed() {
        return childrenSubscribed;
    }

    private static final Logger logger = Logger
	    .getLogger(ChildrenLinkListHandler.class);

    private Set<String> childrenSubscribed = new HashSet<String>();

    @Value("${jms.children.subscription.list.path:}")
    private String serializableSystemPath;

    private static final String SUBSCRIPTION_LIST_FILENAME = "subscription_list.bin";

    public boolean isChildSubscribed(String childIpandPort) {

	boolean childSubscribed = false;

	if (this.childrenSubscribed.contains(childIpandPort)) {
	    childSubscribed = true;
	}
	logger.debug("is "+childIpandPort+" subscribed? "+childSubscribed);
	return childSubscribed;
    }

    public void addChildIpToSubscribedList(String childIpandPort) {
	
	this.childrenSubscribed.add(childIpandPort);
	
	this.saveListInDisk();
    }

    private void saveListInDisk() {

	if (!StringUtils.isEmpty(this.serializableSystemPath)) {
	    this.writeInDiskList();
	}
    }

    private void writeInDiskList() {

	try (FileOutputStream fos = new FileOutputStream(getFileCompletePath());

		ObjectOutputStream out = new ObjectOutputStream(fos);) {

	    out.writeObject(this.childrenSubscribed);

	} catch (FileNotFoundException e) {
	    logger.warn(
		    "it was not posible to write the subscription list in disk because the given path is not valid ",
		    e);
	} catch (IOException e) {
	    logger.warn(
		    "it was not posible to write the subscription list in disk because an IO Exception ",
		    e);
	}
    }

    public void removeChildIpToSubscribedList(String childIpandPort) {
	this.childrenSubscribed.remove(childIpandPort);
    }

    public static boolean isChildSubscription(String completeChildId) {
	return completeChildId.startsWith(TopicActor.SUBSCRIBER_BASE_ID);
    }

    public static String getIpAndPortFromConnectionId(String completeChildId) {

	String childAddressAndPort = "";
	int openBraketPosition = completeChildId
		.indexOf(TopicActor.OPEN_ENCLOSING_BLOCK);
	int closeBraketPosition = completeChildId
		.indexOf(TopicActor.CLOSE_ENCLOSING_BLOCK);
	if (openBraketPosition > 0 && closeBraketPosition > 0) {
	    childAddressAndPort = completeChildId.substring(
		    openBraketPosition + 1, closeBraketPosition);
	}

	return childAddressAndPort;
    }
    
    @PostConstruct
    public void loadList(){
	if(this.childrenSubscribed.isEmpty()){
	   this.readListFromDisk(); 
	}
    }

    @SuppressWarnings("unchecked")
    private void readListFromDisk() {
	try (FileInputStream fis = new FileInputStream(getFileCompletePath());
		ObjectInputStream in = new ObjectInputStream(fis)) {

	    this.childrenSubscribed = (Set<String>) in.readObject();

	} catch (FileNotFoundException e) {
	    logger.warn(
		    "it was not posible to read the subscription list from disk (file not found), "
		    + "check the path in the property file, "
		    + "is a normal behaviour if this is the first run and the list is empty");
	} catch (IOException e) {
	    logger.warn("it was not posible to read the subscription list from disk due an IOException ",e);
	} catch (ClassNotFoundException e) {
	    // is imposible to get an ClassNotFound for Set<String>
	    logger.warn("ClassNotFoundException ",e);
	}
    }

    private String getFileCompletePath() {
	return this.serializableSystemPath + SUBSCRIPTION_LIST_FILENAME;
    }
}
