package com.amazons3.assesment.services;

import java.io.File;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazons3.assesment.exceptions.ApplicationException;

public interface StorageServiceUtility {

	/**
	 * Creates a bucket using s3 client if the bucket doesn't already exist or return the existing bucket
	 * @param bucketName
	 * @return
	 */
	public abstract Bucket createBucket(String bucketName);

	/**
	 * Check if the bucket exists and inserts the object 
	 * @param bucketName
	 * @param objectKey
	 * @param file
	 * @return
	 * @throws ApplicationException
	 */
	public abstract PutObjectResult insertObject(String bucketName,
			String objectKey, File file) throws ApplicationException;

	/**
	 * Deletes the given bucket and all its containing objects 
	 * @param bucketName
	 * @throws ApplicationException
	 */
	public abstract void deleteBucket(String bucketName)
			throws ApplicationException;

	/**
	 * Delete a specific object using its key from a bucket
	 * @param bucketName
	 * @param objectKey
	 */
	public abstract void deleteObject(String bucketName, String objectKey);

	/**
	 * Gets the bucket details using bucket name 
	 * @param bucketName
	 * @return
	 */
	public abstract Bucket getBucket(String bucketName);

	/**
	 * Get object in
	 * @param bucketName
	 * @param key
	 * @return
	 */
	public abstract S3Object getObject(String bucketName, String key);

	/**
	 * Downloads the file details to a specific folder location using the bucket name and object key name 
	 * return true if download is success and false if failed
	 * @param bucketName
	 * @param key
	 * @return
	 */
	public abstract boolean downloadObject(String bucketName, String key,
			String folderLocation);

}