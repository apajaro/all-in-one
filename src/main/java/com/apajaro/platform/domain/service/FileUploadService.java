package com.apajaro.platform.domain.service;

import com.apajaro.platform.domain.entity.FileMetadata;

import java.io.InputStream;

public interface FileUploadService {
    String uploadFile(InputStream content, FileMetadata fileMetadata, String organizationId);
}
