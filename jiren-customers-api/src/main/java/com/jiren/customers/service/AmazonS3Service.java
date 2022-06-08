package com.jiren.customers.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.jiren.shared.aws.s3.S3Utilities;

import java.util.List;

public interface AmazonS3Service {
    AmazonS3 getAmazonS3();
    S3Utilities getS3Utilities();
    String getBucketNameS3(List<Bucket> buckets);
    String getCrecemasAwsS3UrlObj();
}
