package com.demo;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.core.SdkResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Response;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import java.io.IOException;
import java.nio.file.*;

public class DeleteBucket{
    public static void main(String[] args) {
        S3Client s3client = S3Client.builder().region(Region.US_EAST_2).build();
        String bucketName = "athena-output-sanjay-1234";
        HeadBucketRequest headBucketRequest = HeadBucketRequest.builder().bucket(bucketName).build();
    
        try{
            HeadBucketResponse headBucketResponse = s3client.headBucket(headBucketRequest);
            if(headBucketResponse.sdkHttpResponse().statusCode() == 200) {
                String deletionResponse = deleteBucketWithBucketName(bucketName, s3client);
                System.out.println(deletionResponse);
            }
        }
        catch(NoSuchBucketException e){
            System.out.println("The bucket: " + bucketName+ " Doesnot exist");
        }
        catch(S3Exception e){
            System.out.println("Some exception occured while getting metadata " + e.getMessage());
        }
    }
    public static String deleteBucketWithBucketName(String bucketName, S3Client s3client){
        DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder().bucket(bucketName).build();
        try{
        DeleteBucketResponse deleteBucketResponse = s3client.deleteBucket(deleteBucketRequest);
            return "Successfully deleted the bucket";
        }
        catch(S3Exception e){
            return e.getMessage();
        }
    }
}