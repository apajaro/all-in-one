package com.apajaro.platform.infrastructure.data.entity;

import com.apajaro.platform.domain.entity.Organization;
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
@Table(name = "organization")
public class OrganizationDao {
    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String slogan;

    @Builder.Default
    @CreatedDate
    @Column("created_at")
    private Instant createdAt = Instant.now();;

    @Builder.Default
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt = Instant.now();

    public static OrganizationDao fromDomain(Organization organization) {
        return OrganizationDao.builder()
                .id(organization.getId().getValue())
                .name(organization.getName())
                .slogan(organization.getSlogan())
                .build();
    }

    public Organization toDomain() {
        return Organization.builder()
                .id(ID.of(this.id))
                .name(this.name)
                .slogan(this.slogan)
                .build();
    }
}
