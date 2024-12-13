package com.apajaro.platform.application.service;

import com.apajaro.platform.application.BucketService;
import com.apajaro.platform.application.repository.FileMetadataRepository;
import com.apajaro.platform.domain.entity.FileMetadata;
import com.apajaro.platform.domain.exception.DomainException;
import com.apajaro.platform.domain.exception.ErrorCode;
import com.apajaro.platform.domain.service.FileUploadService;
import lombok.AllArgsConstructor;

import java.io.InputStream;
import java.util.Objects;

@AllArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private FileMetadataRepository fileMetadataRepository;
    private BucketService bucketService;

    @Override
    public String uploadFile(InputStream content, FileMetadata fileMetadata, String organizationId) {
        if (Objects.isNull(content)) {
            throw new DomainException(ErrorCode.NO_FILE_FOUND);
        }

        fileMetadataRepository.create(fileMetadata);
        String objectName = organizationId + "/" + fileMetadata.getId().getValue();

        bucketService.putObject(content, "prod-images", objectName, fileMetadata.getContentType());
        return objectName;
    }
}
