package com.apajaro.platform.infrastructure.web.controller.security.dto;

import com.apajaro.platform.domain.entity.SecurityPermission;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SecurityPermissionDto {
    private String id;
    private String name;
    private String slug;
    private String description;

    public static SecurityPermissionDto from(SecurityPermission permission) {
        return SecurityPermissionDto.builder()
                .id(permission.getId().getValue())
                .name(permission.getName())
                .slug(permission.getSlug())
                .description(permission.getDescription())
                .build();
    }

    public SecurityPermission toDomain() {
        return SecurityPermission.builder()
                .id(ID.of(id))
                .name(name)
                .slug(slug)
                .description(description)
                .build();
    }
}