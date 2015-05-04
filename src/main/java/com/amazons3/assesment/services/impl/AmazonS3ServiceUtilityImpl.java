package com.amazons3.assesment.services.impl;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazons3.assesment.exceptions.ApplicationException;
import com.amazons3.assesment.services.StorageServiceUtility;
import com.amazons3.assesment.utils.AmazonServicesConnectionUtils;

/**
 *  * Wrapper class written around AmazonS3 client which provides services to create and handle buckets and objects
 * @author andromeda
 *
 */
public class AmazonS3ServiceUtilityImpl implements StorageServiceUtility {
	
    private static final Logger logger_c = LoggerFactory.getLogger(AmazonS3ServiceUtilityImpl.class);

	//Get s3 client using connection utility class
	private AmazonS3 s3Client = AmazonServicesConnectionUtils.getAmazonS3Connection();
	
	/* (non-Javadoc)
	 * @see com.amazons3.assesment.services.AmazonS3ServiceUtility#createBucket(java.lang.String)
	 */
	public Bucket createBucket(String bucketName) {
		logger_c.debug("AmazonS3ServiceUtilityImpl-createBucket");
		Bucket existingBucket = getBucket(bucketName);
		if(existingBucket != null){
			return existingBucket;
		}	
		return s3Client.createBucket(bucketName);
	}	
	
	/* (non-Javadoc)
	 * @see com.amazons3.assesment.services.AmazonS3ServiceUtility#insertObject(java.lang.String, java.lang.String, java.io.File)
	 */
	public PutObjectResult insertObject(String bucketName, String objectKey, File file) throws ApplicationException {
		logger_c.debug("AmazonS3ServiceUtilityImpl-insertObject");
		Bucket existingBucket = getBucket(bucketName);
		if(existingBucket == null){	
			throw new ApplicationException("Bucket not found exception");
		}		
		return s3Client.putObject(new PutObjectRequest(bucketName, objectKey, file));
	}
	
	/* (non-Javadoc)
	 * @see com.amazons3.assesment.services.AmazonS3ServiceUtility#deleteBucket(java.lang.String)
	 */
	public void deleteBucket(String bucketName) throws ApplicationException {	
		logger_c.debug("AmazonS3ServiceUtilityImpl-deleteBucket");
		Bucket existingBucket = getBucket(bucketName);
		if(existingBucket == null){	
			throw new ApplicationException("Bucket not found exception");
		}		
		s3Client.deleteBucket(bucketName);		
	}
	
	/* (non-Javadoc)
	 * @see com.amazons3.assesment.services.AmazonS3ServiceUtility#deleteObject(java.lang.String, java.lang.String)
	 */
	public void deleteObject(String bucketName, String objectKey) {		
		logger_c.debug("AmazonS3ServiceUtilityImpl-deleteObject");
		s3Client.deleteObject(bucketName, objectKey);
	}
	
	/* (non-Javadoc)
	 * @see com.amazons3.assesment.services.AmazonS3ServiceUtility#getBucket(java.lang.String)
	 */
	public Bucket getBucket(String bucketName) {
		List<Bucket> existingBucketList = s3Client.listBuckets();		
		for(Bucket bucket : existingBucketList) {
			if(bucketName.equals(bucket.getName())) {
				return bucket;
			}
		}
		return null;		
	}
	
	/* (non-Javadoc)
	 * @see com.amazons3.assesment.services.AmazonS3ServiceUtility#getObject(java.lang.String, java.lang.String)
	 */
	public S3Object getObject(String bucketName, String key) {
	    S3Object object = s3Client.getObject(bucketName, key); 
	    return object;		
	}
	
	/* (non-Javadoc)
	 * @see com.amazons3.assesment.services.AmazonS3ServiceUtility#downloadObject(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean downloadObject(String bucketName, String key, String folderLocation) {
		File localFile = new File(folderLocation+"/"+key);
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
		s3Client.getObject(getObjectRequest,localFile);	
		return localFile.exists() && localFile.canRead(); 	
	}
	
	
}
