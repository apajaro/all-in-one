package com.apajaro.platform.infrastructure.data.entity;

import com.apajaro.platform.domain.entity.SecurityGroup;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Table(name = "security_group")
public class SecurityGroupDao {
    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String description;

    @Column("organization_id")
    private String organizationId;

    @Builder.Default
    @MappedCollection(idColumn = "security_group_id")
    private Set<SecurityPermissionRef> permissions = new HashSet<>();

    @Builder.Default
    @CreatedDate
    @Column("created_at")
    private Instant createdAt = Instant.now();;

    @Builder.Default
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt = Instant.now();

    public void addSecurityPermission(ID securityPermissionId) {
        permissions.add(new SecurityPermissionRef(securityPermissionId.getValue()));
    }

    public SecurityGroup toDomain() {
        SecurityGroup securityGroup = SecurityGroup
                .builder()
                .id(ID.of(this.id))
                .name(this.name)
                .description(this.description)
                .build();

        permissions.forEach(permission -> securityGroup.addSecurityPermission(ID.of(permission.securityPermissionId)));
        return securityGroup;
    }

    public static SecurityGroupDao fromDomain(SecurityGroup securityGroup, String organizationId) {
        SecurityGroupDao securityGroupDao = SecurityGroupDao
                .builder()
                .id(securityGroup.getId().getValue())
                .name(securityGroup.getName())
                .description(securityGroup.getDescription())
                .organizationId(organizationId)
                .build();

        securityGroup.getPermissions().forEach(securityGroupDao::addSecurityPermission);
        return securityGroupDao;
    }
}
