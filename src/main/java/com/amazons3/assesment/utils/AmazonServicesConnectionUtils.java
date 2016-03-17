package com.amazons3.assesment.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;

/**
 * Utility class provides clients for amazons3, amazonSQS 
 * @author andromeda
 *
 */
public class AmazonServicesConnectionUtils {
	
	//Hard coded credentials which should later be moved to properties file 
 	private static final String accessKey = "";
	private static final String secretKey = "";
    private static final Region usEast1 = Region.getRegion(Regions.US_WEST_2);

    /**
     * private prevents class from instantiating
     */
	private AmazonServicesConnectionUtils() {
		
	}
	
	/**
	 * Utility method returns a amazon s3 client object
	 * @return
	 */
	public static AmazonS3 getAmazonS3Connection() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
    	AmazonS3 amazonS3Client = new AmazonS3Client(credentials);
    	amazonS3Client.setEndpoint("objects.assesment.com");
    	amazonS3Client.setRegion(usEast1);
        return amazonS3Client;
	}
	
	/**
	 * Utility method return a amazon SQS client object 
	 * @return
	 */
	public static AmazonSQS getAmazonSQSConnectionClient() {		
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonSQS amazonSQSClient = new AmazonSQSClient(credentials);
        amazonSQSClient.setRegion(usEast1);
        return amazonSQSClient;
		
	}
}
