package com.apajaro.platform.application;

import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.List;

public interface BucketService {
    void createBucket(String bucketName);

    List<Bucket> listBuckets();

    void deleteBucket(String bucketName);

    void putObject(InputStream object, String bucketName, String objectName, String contentType);

    List<S3Object> listObjects(String bucketName);

    void getObject(String bucketName, String objectName);

    void deleteObject(String bucketName, String objectName);

    void deleteObjects(String bucketName, List<String> objects);

    void moveObject(String sourceBucketName, String objectName, String targetBucketName);
}
