package com.ncr.serverchain;

import com.ncr.serverchain.message.specific.SpecificMessage;
import com.ncr.serverchain.message.wrapper.MessageWrapper;

/**
 * <pre>
 * Class in charge of publishing a message into a topic.
 * 
 * The defaults values for the wrapper message and id are:
 * * id: -1
 * * message: blank string
 * 
 * 
 * Publish an outgoing message means that the message will travel from the first node (root) to the leafs.
 * 
 * Publish an incoming message means that the message will travel from the the leafs to the first node (root) 
 * 
 * @author Otto Abreu
 * </pre>
 */
public interface MessagePublisher {

    /**
     * sends the given MessageWrapper to the outgoing topic
     * 
     * @param wrappedMessage
     *            MessageWrapper
     */
    public void publishOutgoingMessage(MessageWrapper wrappedMessage);

    /**
     * Instantiate a MessageWrapper with defaults id and message, adds the
     * SpecificMessage into the wrapper and send it to the outgoing topic
     * 
     * @param wrappedMessage
     */
    public void publishOutgoingMessage(SpecificMessage wrappedMessage);

    /**
     * Instantiate a MessageWrapper with the given id and default message, adds
     * the SpecificMessage into the wrapper and send it to the outgoing topic
     * 
     * @param id
     *            wrapper id
     * @param wrappedMessage
     */
    public void publishOutgoingMessage(long id, SpecificMessage wrappedMessage);

    /**
     * Instantiate a MessageWrapper with the given wrapper message and default
     * id, adds the SpecificMessage into the wrapper and send it to the outgoing
     * topic
     * 
     * @param id
     *            wrapper id
     * @param wrappedMessage
     */
    public void publishOutgoingMessage(String wraperMessage,
	    SpecificMessage wrappedMessage);

    /**
     * Instantiate a MessageWrapper with the given id and message, adds the
     * SpecificMessage into the wrapper and send it to the outgoing topic
     * 
     * @param id
     *            wrapper id
     * @param wrapperMessage
     *            message to add in the wrapper
     * @param wrappedMessage
     *            SpecificMessage
     */
    public void publishOutgoingMessage(long id, String wrapperMessage,
	    SpecificMessage wrappedMessage);

    /**
     * sends the given MessageWrapper to the incoming topic
     * 
     * @param wrappedMessage
     *            MessageWrapper
     */
    public void publishIncomingMessage(MessageWrapper wrappedMessage);

    /**
     * Instantiate a MessageWrapper with defaults id and message, adds the
     * SpecificMessage into the wrapper and send it to the incoming topic
     * 
     * @param wrappedMessage
     */
    public void publishIncomingMessage(SpecificMessage wrappedMessage);

    /**
     * Instantiate a MessageWrapper with the given id and default message, adds
     * the SpecificMessage into the wrapper and send it to the incoming topic
     * 
     * @param id
     *            wrapper id
     * @param wrappedMessage
     */
    public void publishIncomingMessage(long id, SpecificMessage wrappedMessage);

    /**
     * Instantiate a MessageWrapper with the given wrapper message and default
     * id, adds the SpecificMessage into the wrapper and send it to the incoming
     * topic
     * 
     * @param id
     *            wrapper id
     * @param wrappedMessage
     */
    public void publishIncomingMessage(String wraperMessage,
	    SpecificMessage wrappedMessage);

    /**
     * Instantiate a MessageWrapper with the given id and message, adds the
     * SpecificMessage into the wrapper and send it to the incoming topic
     * 
     * @param id
     *            wrapper id
     * @param wrapperMessage
     *            message to add in the wrapper
     * @param wrappedMessage
     *            SpecificMessage
     */
    public void publishIncomingMessage(long id, String wrapperMessage,
	    SpecificMessage wrappedMessage);

}
