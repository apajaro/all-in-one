package com.apajaro.platform.domain.entity;

import com.apajaro.platform.domain.valueobject.ID;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
public class SecurityGroup {
    ID id;
    String name;
    String description;
    @Builder.Default
    Set<ID> permissions = new HashSet<>();

    public void addSecurityPermission(ID securityPermissionId) {
        permissions.add(securityPermissionId);
    }

    public static SecurityGroup of(String id) {
        return SecurityGroup.builder().id(ID.of(id)).build();
    }

    public static SecurityGroup of(String id, String name) {
        return SecurityGroup.builder()
                .id(ID.of(id))
                .name(name)
                .build();
    }

    public Map<String, String> toMap() {
        return Map.of(
                "id", id.getValue(),
                "name", Optional.ofNullable(name).orElse(""),
                "description", Optional.ofNullable(description).orElse("")
        );
    }
}
