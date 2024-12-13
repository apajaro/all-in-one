package com.apajaro.platform.infrastructure.data.entity;

import com.apajaro.platform.domain.entity.FileMetadata;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Builder
@Table(name = "file_metadata")
public class FileMetadataDao {
    @Id
    private String id;

    @Column
    private Long size;

    @Column
    private String contentType;

    @Builder.Default
    @CreatedDate
    @Column("created_at")
    private Instant createdAt = Instant.now();;

    @Builder.Default
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt = Instant.now();

    public static FileMetadataDao fromDomain(FileMetadata fileMetadata) {
        return FileMetadataDao.builder()
                .id(fileMetadata.getId().getValue())
                .size(fileMetadata.getSize())
                .contentType(fileMetadata.getContentType())
                .build();
    }

    public FileMetadata toDomain() {
        return FileMetadata.builder()
                .id(ID.of(this.id))
                .size(this.size)
                .contentType(this.contentType)
                .build();
    }
}
