# AWSAssesment
Simple AWS test program to upload objects to amazon s3 and storing the objects keys to SQS for use of retriving objects from S3

Scope of this App:
This program only uploads 10 files on each run to s3 and downloads the same from s3 to the folder "s3Downloads".


Steps to run the project:

1) Import the project into work space as maven project
2) package the project using maven package command 
3) Run the main() class AmazonS3SQSApp.java as Java application. 
4) This should upload the files to S3 folder and the same are downloaded using the object keys from SQS to "s3Downloads" folder 
on the class path.
