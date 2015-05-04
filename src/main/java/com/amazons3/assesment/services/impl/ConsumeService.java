package com.amazons3.assesment.services.impl;

import static com.amazons3.assesment.utils.ApplicationConstants.BUCKET_NAME;
import static com.amazons3.assesment.utils.ApplicationConstants.DOWNLOADS_FOLDER;
import static com.amazons3.assesment.utils.ApplicationConstants.QUEUE_NAME;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.sqs.model.Message;
import com.amazons3.assesment.services.MessageQueueServiceUtility;
import com.amazons3.assesment.services.StorageServiceUtility;

/**
 * Consume Service downloads the files from Amazon S3 and stores them to a local folder on disk
 * @author andromeda
 *
 */
public class ConsumeService {
	
    private static final Logger logger_c = LoggerFactory.getLogger(ConsumeService.class);
	
	private StorageServiceUtility amazonS3ServiceUtility;
	private MessageQueueServiceUtility amazonSQSServiceUtility;	
	private String queueURL;
	
	public ConsumeService() {		
		 amazonS3ServiceUtility = new AmazonS3ServiceUtilityImpl();
		 amazonSQSServiceUtility = new AmazonSQSServiceUtilityImpl();
		 queueURL = amazonSQSServiceUtility.getQueueUrl(QUEUE_NAME);
	}
	
	/**
	 * Consume files is a facade method which serves as entry point for downloading files stored in amazon S3
	 */
	public void consumeFiles() {
		List<Message> messages = amazonSQSServiceUtility.getMessagesFromQueue(queueURL);
		downloadFiles(messages);			
	}
	
	/**
	 * Downloads the files to a folder on disk using the list of message keys and the bucket details
	 * @param objectKeys
	 */
	private void downloadFiles(List<Message> objectKeys) {
		for(Message objectKey: objectKeys) {
			amazonS3ServiceUtility.downloadObject(BUCKET_NAME, objectKey.getBody(), DOWNLOADS_FOLDER);
			amazonSQSServiceUtility.deleteMessageFromQueue(queueURL, objectKey);
		}	
	}
}
