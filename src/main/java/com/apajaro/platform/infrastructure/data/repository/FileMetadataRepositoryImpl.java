package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.application.repository.FileMetadataRepository;
import com.apajaro.platform.domain.entity.FileMetadata;
import com.apajaro.platform.infrastructure.data.entity.FileMetadataDao;
import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class FileMetadataRepositoryImpl implements FileMetadataRepository {
    private final FileMetadataRepositoryJdbc repository;
    private final JdbcAggregateTemplate template;

    @Override
    public Optional<FileMetadata> findById(String fileMetadataId) {
        return repository.findById(fileMetadataId).map(FileMetadataDao::toDomain);
    }

    @Override
    public FileMetadata create(FileMetadata fileMetadata) {
        return template.insert(FileMetadataDao.fromDomain(fileMetadata)).toDomain();
    }

    @Override
    public FileMetadata update(FileMetadata fileMetadata) {
        return template.update(FileMetadataDao.fromDomain(fileMetadata)).toDomain();
    }
}
