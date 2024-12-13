package com.apajaro.platform.infrastructure.data;

import com.apajaro.platform.application.BucketService;
import com.apajaro.platform.application.Logger;
import com.apajaro.platform.domain.exception.DomainException;
import com.apajaro.platform.domain.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3Service implements BucketService {
    private final S3Client s3Client;
    private final Logger logger;

    public void createBucket(String bucketName) {
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        try {
            s3Client.createBucket(createBucketRequest);
            logger.info("Bucket created: {" + createBucketRequest.bucket() + "}");
        } catch (S3Exception e) {
            logger.error("Error creating bucket: {" + e.awsErrorDetails().errorMessage() + "}");
            throw new DomainException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Bucket> listBuckets() {
        return s3Client.listBuckets().buckets();
    }

    public void deleteBucket(String bucketName) {
        DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder()
                .bucket(bucketName)
                .build();

        try {
            s3Client.deleteBucket(deleteBucketRequest);
            logger.info("Bucket deleted: {" + deleteBucketRequest.bucket() + "}");
        } catch (S3Exception e) {
            logger.error("Error deleting bucket: { " + e.awsErrorDetails().errorMessage() + " }");
            throw new DomainException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void putObject(InputStream object, String bucketName, String objectName, String contentType) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectName)
                .contentType(contentType)
                .build();

        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(object.readAllBytes()));
            logger.info("Object created: { " + putObjectRequest.key() + " }");
        } catch(NoSuchBucketException e) {
            createBucket(bucketName);
            putObject(object, bucketName, objectName, contentType);
        } catch (S3Exception e) {
            logger.error("Error uploading object: { " + e.awsErrorDetails().errorMessage() + " }");
            throw new DomainException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DomainException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public List<S3Object> listObjects(String bucketName) {
        ListObjectsRequest listObjects = ListObjectsRequest
                .builder()
                .bucket(bucketName)
                .build();

        try {
            ListObjectsResponse objectListing = s3Client.listObjects(listObjects);
            return objectListing.contents();
        } catch (S3Exception e) {
            logger.error(e.awsErrorDetails().errorMessage());
        }

        throw new DomainException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    public void getObject(String bucketName, String objectName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        try {
            ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(getObjectRequest);
            FileUtils.copyInputStreamToFile(responseInputStream, new File("." + File.separator + objectName));
        } catch (S3Exception e) {
            logger.error(e.awsErrorDetails().errorMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new DomainException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteObject(String bucketName, String objectName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        try {
            s3Client.deleteObject(deleteObjectRequest);
            logger.info("Object deleted: { " + deleteObjectRequest.key() + " }");
        } catch (S3Exception e) {
            logger.error("Error deleting object: { " + e.awsErrorDetails().errorMessage() + " }");
            throw new DomainException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteObjects(String bucketName, List<String> objects) {
        ArrayList<ObjectIdentifier> objectIdentifiers = new ArrayList<>();

        objects.forEach(object -> {
            ObjectIdentifier objectIdentifier = ObjectIdentifier.builder()
                    .key(object)
                    .build();

            objectIdentifiers.add(objectIdentifier);
        });

        Delete delete = Delete.builder()
                .objects(objectIdentifiers)
                .build();

        DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
                .bucket(bucketName)
                .delete(delete)
                .build();

        try {
            s3Client.deleteObjects(deleteObjectsRequest);

            String identifiers = objectIdentifiers.stream()
                    .map(ObjectIdentifier::key)
                    .collect(Collectors.joining());

            logger.info("Objects deleted: {" + identifiers + "}");
        } catch (S3Exception e) {
            logger.error("Error deleting objects: { " + e.awsErrorDetails().errorMessage() + " }");
            throw new DomainException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void moveObject(String sourceBucketName, String objectName, String targetBucketName) {
        CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                .sourceBucket(sourceBucketName)
                .sourceKey(objectName)
                .destinationBucket(targetBucketName)
                .destinationKey(objectName)
                .build();

        try {
            CopyObjectResponse copyObjectResponse = s3Client.copyObject(copyObjectRequest);

            if (copyObjectResponse != null) {
                deleteObject(sourceBucketName, objectName);
                logger.info("Object moved and deleted: { " + copyObjectRequest.sourceKey() + " }");
            }
        } catch (S3Exception e) {
            logger.error(e.awsErrorDetails().errorMessage());
            throw new DomainException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
