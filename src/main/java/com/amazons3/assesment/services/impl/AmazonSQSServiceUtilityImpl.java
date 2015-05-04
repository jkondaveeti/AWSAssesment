package com.amazons3.assesment.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.amazons3.assesment.services.MessageQueueServiceUtility;
import com.amazons3.assesment.utils.AmazonServicesConnectionUtils;

/**
 * Wrapper class written around AmazonSQS client which provides services to create and handle queues
 * @author andromeda
 *
 */
public class AmazonSQSServiceUtilityImpl implements MessageQueueServiceUtility {
	
    private static final Logger logger_c = LoggerFactory.getLogger(AmazonSQSServiceUtilityImpl.class);
	
	private AmazonSQS sqsClient = AmazonServicesConnectionUtils.getAmazonSQSConnectionClient();

	 /* (non-Javadoc)
	 * @see com.amazons3.assesment.services.AmazonSQSServiceUtility#createQueue(java.lang.String)
	 */
    public String createQueue(String queueName){
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
        String queueUrl = sqsClient.createQueue(createQueueRequest).getQueueUrl();
        return queueUrl;
    }

    /* (non-Javadoc)
	 * @see com.amazons3.assesment.services.AmazonSQSServiceUtility#getQueueUrl(java.lang.String)
	 */
    public String getQueueUrl(String queueName){
        GetQueueUrlRequest getQueueUrlRequest = new GetQueueUrlRequest(queueName);
        return sqsClient.getQueueUrl(getQueueUrlRequest).getQueueUrl();
    }

    /* (non-Javadoc)
	 * @see com.amazons3.assesment.services.AmazonSQSServiceUtility#listQueues()
	 */
    public ListQueuesResult listQueues(){
       return sqsClient.listQueues();
    }

    /* (non-Javadoc)
	 * @see com.amazons3.assesment.services.AmazonSQSServiceUtility#sendMessageToQueue(java.lang.String, java.lang.String)
	 */
    public void sendMessageToQueue(String queueUrl, String message){
        SendMessageResult messageResult =  sqsClient.sendMessage(new SendMessageRequest(queueUrl, message));
    }

    /* (non-Javadoc)
	 * @see com.amazons3.assesment.services.AmazonSQSServiceUtility#getMessagesFromQueue(java.lang.String)
	 */
    public List<Message> getMessagesFromQueue(String queueUrl){
       ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
       receiveMessageRequest.setMaxNumberOfMessages(10);       
       List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();
       return messages;
    }

    /* (non-Javadoc)
	 * @see com.amazons3.assesment.services.AmazonSQSServiceUtility#deleteMessageFromQueue(java.lang.String, com.amazonaws.services.sqs.model.Message)
	 */
    public void deleteMessageFromQueue(String queueUrl, Message message){
        String messageRecieptHandle = message.getReceiptHandle();
        sqsClient.deleteMessage(new DeleteMessageRequest(queueUrl, messageRecieptHandle));
    }


}
