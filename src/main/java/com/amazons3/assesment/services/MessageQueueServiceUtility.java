package com.amazons3.assesment.services;

import java.util.List;

import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;

public interface MessageQueueServiceUtility {

	/**
	 * Creates a queue using sqs client and returns the url of the queue
	 * @param queueName
	 * @return
	 */
	public abstract String createQueue(String queueName);

	/**
	 * returns the queueurl for for sqs queue if you pass in a name
	 * @param queueName
	 * @return
	 */
	public abstract String getQueueUrl(String queueName);

	/**
	 * lists all your queue using sqs client.
	 * @return
	 */
	public abstract ListQueuesResult listQueues();

	/**
	 * send a single message to your sqs queue
	 * @param queueUrl
	 * @param message
	 */
	public abstract void sendMessageToQueue(String queueUrl, String message);

	/**
	 * gets messages from your queue
	 * @param queueUrl
	 * @return
	 */
	public abstract List<Message> getMessagesFromQueue(String queueUrl);

	/**
	 * deletes a single message from your queue.
	 * @param queueUrl
	 * @param message
	 */
	public abstract void deleteMessageFromQueue(String queueUrl, Message message);

}