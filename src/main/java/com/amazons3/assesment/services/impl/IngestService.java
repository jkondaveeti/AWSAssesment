package com.amazons3.assesment.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazons3.assesment.exceptions.ApplicationException;
import com.amazons3.assesment.services.MessageQueueServiceUtility;
import com.amazons3.assesment.services.StorageServiceUtility;
import com.amazons3.assesment.utils.ApplicationConstants;
import com.amazons3.assesment.utils.ApplicationUtils;

/**
 * Ingest Service contains methods to upload the files to Amazon S3 area and queue the object keys to 
 * for the uploaded file object keys to SQS queue.  
 * @author andromeda
 *
 */
public class IngestService {
	
    private static final Logger logger_c = LoggerFactory.getLogger(IngestService.class);
	//Services to interact with S3, SQS
	private StorageServiceUtility amazonS3ServiceUtility;
	private MessageQueueServiceUtility amazonSQSServiceUtility;
	//Q
	private String queueURL;
	private Map<String,File> uploadFileDetailsMap = new HashMap<String, File>();
	

	/**
	 * 
	 */
	public IngestService() {		
		 amazonS3ServiceUtility = new AmazonS3ServiceUtilityImpl();
		 amazonSQSServiceUtility = new AmazonSQSServiceUtilityImpl();
		 amazonS3ServiceUtility.createBucket(ApplicationConstants.BUCKET_NAME);		
		 queueURL = amazonSQSServiceUtility.createQueue(ApplicationConstants.QUEUE_NAME);
	}
	
	/**
	 * Facade method which prepares the file data, upload it to S3, queues the object keys
	 * @throws IOException
	 * @throws ApplicationException
	 */
	public void ingestFiles() throws IOException, ApplicationException {
		prepareUploadFileDetails(10); 		//prepare ten files to upload
		uploadFilesToS3(); // upload prepared files to S3
		queueMessages(); //put the object keys in SQS 
	}
	
	/**
	 * Loop through the upload file details and put the files in S3 area
	 * @throws ApplicationException
	 */
	private void uploadFilesToS3() throws ApplicationException {	
			 Iterator<Entry<String, File>> iterator = uploadFileDetailsMap.entrySet().iterator();
			    while (iterator.hasNext()) {
			        Map.Entry<String, File> pair = (Map.Entry<String, File>)iterator.next();			        
					amazonS3ServiceUtility.insertObject(ApplicationConstants.BUCKET_NAME, pair.getKey(), pair.getValue());
			    }		
		}
	
	/**
	 * Prepare sample files and assign each file with a object key and put the details to upload 
	 * file details map 
	 * @param numberOfFiles
	 * @return
	 * @throws IOException
	 */
	private void prepareUploadFileDetails(int numberOfFiles) throws IOException {	
		for(int counter=0; counter<numberOfFiles; counter++) {
			String objectKey = ApplicationConstants.OBJECT_NAME_PREFIX+counter;
			File randomFile = ApplicationUtils.createSampleFile(objectKey+UUID.randomUUID(), ApplicationUtils.createSampleData(10));
			uploadFileDetailsMap.put(objectKey, randomFile);
		}		
	}
	
	/**
	 * Put the messages to already created SQS queue
	 */
	private void queueMessages() {		
		for(String objectKey: uploadFileDetailsMap.keySet()) {
			amazonSQSServiceUtility.sendMessageToQueue(queueURL, objectKey);
		}
	}
}

	

