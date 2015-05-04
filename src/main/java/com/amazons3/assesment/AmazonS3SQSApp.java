package com.amazons3.assesment;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazons3.assesment.exceptions.ApplicationException;
import com.amazons3.assesment.services.impl.ConsumeService;
import com.amazons3.assesment.services.impl.IngestService;

public class AmazonS3SQSApp {

    private static final Logger logger_c = LoggerFactory.getLogger(AmazonS3SQSApp.class);
    		
	public static void main(String[] args) throws IOException, ApplicationException {
		logger_c.info("Starting to ingest files to S3 bucket");
		IngestService ingestService = new IngestService();
		ingestService.ingestFiles();
		logger_c.info("End of ingest files to S3 bucket");

		logger_c.info("Starting to consume files from S3 bucket");
		ConsumeService consumeService = new ConsumeService();
		consumeService.consumeFiles();
		logger_c.info("End to consuming files from S3 bucket");
	}

}
