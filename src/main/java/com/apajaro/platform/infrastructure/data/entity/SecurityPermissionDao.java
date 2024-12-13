package com.apajaro.platform.infrastructure.data.entity;

import com.apajaro.platform.domain.entity.SecurityPermission;
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
@Table(name = "security_permission")
public class SecurityPermissionDao {
    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String slug;

    @Column
    private String description;

    @Column("language_code")
    private String languageCode;

    @Builder.Default
    @CreatedDate
    @Column("created_at")
    private Instant createdAt = Instant.now();;

    @Builder.Default
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt = Instant.now();

    public SecurityPermission toDomain() {
        return SecurityPermission.builder()
                .id(ID.of(this.id))
                .name(this.name)
                .slug(this.slug)
                .description(this.description)
                .build();
    }

    public static SecurityPermissionDao fromDomain(SecurityPermission entity) {
        return SecurityPermissionDao
                .builder()
                .id(entity.getId().getValue())
                .name(entity.getName())
                .slug(entity.getSlug())
                .description(entity.getDescription())
                .build();
    }
}
