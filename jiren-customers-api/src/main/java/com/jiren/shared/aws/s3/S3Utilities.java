package com.jiren.shared.aws.s3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class S3Utilities {

    /**
     * Create your credentials file at ~/.aws/credentials
     * (C:\Users\USER_NAME\.aws\credentials for Windows users) and save the
     * following lines after replacing the underlined values with your own.
     * [default] 
     * aws_access_key_id = YOUR_ACCESS_KEY_ID 
     * aws_secret_access_key = YOUR_SECRET_ACCESS_KEY
     */
    public AmazonS3 createConnectionAWSCredentials(String accessKey, String secretKey, String endpoint, String region) {
        AmazonS3 s3 = null;
        try {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

            s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(Regions.fromName(region))
                    .build();

            log.info("Connection object ..." + s3);
            log.info("===========================================");
            log.info("Getting Started with Amazon S3");
            log.info("===========================================\n");
        } catch (AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            log.info("Error Message: " + ace.getMessage());
        } catch (Exception e) {
            log.error("Error in the AWS S3... " + e.getMessage());
        }
        return s3;
    }

    /**
     * Create a new S3 bucket - Amazon S3 bucket names are globally unique, so once
     * a bucket name has been taken by any user, you can't create another bucket
     * with that same name.
     *
     * You can optionally specify a location for your bucket if you want to keep
     * your data closer to your applications or users.
     */
    public AmazonS3 createBucket(AmazonS3 s3, String bucketName) {
        try {
            log.info("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);
        } catch (AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            log.info("Error Message: " + ace.getMessage());
        } catch (Exception e) {
            log.error("Error in the AWS S3... " + e.getMessage());
        }
        return s3;
    }

    /**
     * List the buckets in your account Show the list of Objects in the bucket
     */
    public List<Bucket> listBuckets(AmazonS3 s3) {
        try {
            log.info("Listing buckets");
            for (Bucket bucket : s3.listBuckets()) {
                String bucketnName = bucket.getName();
                log.info(" - " + bucketnName);
                ObjectListing objects = s3.listObjects(bucketnName);
                for (S3ObjectSummary summary : objects.getObjectSummaries()) {
                    log.info(summary.getKey() + "   " + summary.getOwner());
                }
            }
            log.info("\n");
        } catch (AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            log.info("Error Message: " + ace.getMessage());
        } catch (Exception e) {
            log.error("Error in the AWS S3... " + e.getMessage());
        }
        return s3.listBuckets();
    }

    /**
     * Upload an object to your bucket - You can easily upload a file to S3, or
     * upload directly an InputStream if you know the length of the data in the
     * stream. You can also specify your own metadata when uploading to S3, which
     * allows you set a variety of options like content-type and content-encoding,
     * plus additional metadata specific to your applications.
     */
    public void uploadObjectInBucket(AmazonS3 s3, String bucketName, String key, File file) {
        try {
            log.info("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, key, file));
        } catch (AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            log.info("Error Message: " + ace.getMessage());
        } catch (Exception e) {
            log.error("Error in the AWS S3... " + e.getMessage());
        }
    }

    /**
     * Creates a temporary file with text data to demonstrate uploading a file to
     * Amazon S3
     *
     * @return A newly created temporary file with text data.
     * @throws IOException
     */
    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }

    /**
     * Displays the contents of the specified input stream as text.
     *
     * @param input The input stream to display as text.
     * @throws IOException
     */
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null)
                break;

            log.info("    " + line);
        }
        log.info("\n");
    }

    /**
     * Download object from Amazon S3
     *
     * @param amazonS3
     * @param file
     * @param bucketName
     * @param key
     * @throws IOException
     */
    public void downloadObject(AmazonS3 amazonS3, File file, String bucketName, String key) throws IOException {
        try {
            S3Object fullObject = amazonS3.getObject(new GetObjectRequest(bucketName, key));
            FileUtils.copyInputStreamToFile(fullObject.getObjectContent(), file);
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            log.info("Error Message: " + ace.getMessage());
        } catch (Exception e) {
            log.error("Error in the AWS S3... " + e.getMessage());
        }
    }

}
