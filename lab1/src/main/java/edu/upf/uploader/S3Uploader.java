package edu.upf.uploader;

import java.io.File;
import java.util.List;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.HeadBucketRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3Uploader implements Uploader {

    final String bucket;
    String prefix;
    AmazonS3 client;

    public S3Uploader (String bucket, String prefix) {
        this.bucket = bucket;
        this.prefix = prefix;
        this.client = AmazonS3ClientBuilder.defaultClient();
    }

    public boolean bucketExists() {
        try {
            this.client.headBucket(new HeadBucketRequest(this.bucket));
            System.out.println("Bucket '" + this.bucket + "' exists");
        } catch (AmazonS3Exception e) {
            if (e.getStatusCode() == 404) {
                System.out.println("Bucket '" + this.bucket + "' does not exist");
            } else {
                System.out.println("An error occurred while checking if the bucket exists: " + e.getMessage());
            }
            return false;
        }
        return true;
    }
    
    @Override
    public void upload(List<String> files) {

        for (String fileName: files){
            String key = (this.prefix + "/" + fileName).toString();
            System.out.println("Uploading to S3: " + key + "...");
            File file = new File(fileName);
            if (this.bucketExists()) {
                PutObjectRequest putObjectRequest = new PutObjectRequest(this.bucket, key, file);
                this.client.putObject(putObjectRequest);
                System.out.println("File uploaded to S3 successfully: " + fileName);
            }
        }
    }
}
