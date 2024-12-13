package com.apajaro.platform.application.repository;

import com.apajaro.platform.domain.entity.FileMetadata;

import java.util.Optional;

public interface FileMetadataRepository {
    Optional<FileMetadata> findById(String fileMetadataId);

    FileMetadata create(FileMetadata fileMetadata);
    FileMetadata update(FileMetadata fileMetadata);
}
