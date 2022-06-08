package com.jiren.customers.infraestructure.interfaces.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.jiren.customers.service.AmazonS3Service;
import com.jiren.shared.aws.s3.S3Utilities;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestAmazonS3Service implements AmazonS3Service {
	
	@Value("${amazon.s3.access-key}")
    private String accessKey;
   
	@Value("${amazon.s3.secret-key}")
	private String secretKey;
	
	@Value("${amazon.s3.endpoint}")
	private String endpoint;
	
	@Value("${amazon.s3.region}")
	private String region;
    
	@Value("${amazon.s3.bucket-name}")
	private String bucketName;
	
	@Value("${amazon.s3.url-obj}")
	private String urlObj;
    
    @Override
    @Transactional(readOnly = true)
    public AmazonS3 getAmazonS3() {
        return S3Utilities.builder().build().createConnectionAWSCredentials(accessKey, secretKey,  endpoint, region);
    }
    
    @Override
    public S3Utilities getS3Utilities() {
    	return S3Utilities.builder().build();
    }

    @Override
    @Transactional(readOnly = true)
    public String getBucketNameS3(List<Bucket> buckets) {
        for (Bucket bucket : buckets) {
            if (bucket.getName().equals(bucketName)) {
                return bucket.getName();
            }
        }
        return "";
    }

    @Override
    @Transactional(readOnly = true)
    public String getCrecemasAwsS3UrlObj() {
        return urlObj;
    }

}
