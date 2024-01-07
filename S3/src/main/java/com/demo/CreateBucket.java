package com.demo;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.core.SdkResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Response;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import java.io.IOException;
import java.nio.file.*;

public class CreateBucket
{
    public static void main( String[] args )
    {  
        String bucketName = "athena-output-sanjay-123456";
        S3Client s3client = S3Client.builder().region(Region.US_EAST_2).build();
        
        //Checking if the bucket with the given name already exists.
        HeadBucketRequest headBucketRequest = HeadBucketRequest.builder().bucket(bucketName).build();
        try{
            HeadBucketResponse headBucketResponse = s3client.headBucket(headBucketRequest);
            int statusCode = headBucketResponse.sdkHttpResponse().statusCode();
            //If the status code is 200, it means a bucket with the same already exists.
            if(statusCode == 200){
                System.out.println("There exists a bucket with the same name");
            }
        }
        //If there is no such bucket, it throws NoSuchBucketException so, we calling the createBucketWithBucketName method to create bucket.
        catch(NoSuchBucketException e){
            String bucketCreationResponse = createBucketWithBucketName(bucketName, s3client);
            System.out.println(bucketCreationResponse);
        }
        catch(S3Exception e){
            System.out.println("There was an error connecting to S3 client");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    //Custom method to create a bucket.
    public static String createBucketWithBucketName(String bucketName, S3Client s3client){
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder().bucket(bucketName).build();
        try{
        CreateBucketResponse createBucketResponse = s3client.createBucket(createBucketRequest);
        int statusCode = createBucketResponse.sdkHttpResponse().statusCode();
        if(statusCode == 200) {
            return "Bucket: " +bucketName +" is successfully created";
            }
        }
        catch(S3Exception e){
           String errorMessage = e.getMessage();
           return errorMessage;
        }
        
        return " ";
    }
}
