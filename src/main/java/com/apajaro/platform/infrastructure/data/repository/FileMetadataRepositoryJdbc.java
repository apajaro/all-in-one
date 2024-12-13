package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.infrastructure.data.entity.FileMetadataDao;
import org.springframework.data.repository.ListCrudRepository;

public interface FileMetadataRepositoryJdbc extends ListCrudRepository<FileMetadataDao, String> {
}
