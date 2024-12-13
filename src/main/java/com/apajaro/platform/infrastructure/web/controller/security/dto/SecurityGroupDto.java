package com.apajaro.platform.infrastructure.web.controller.security.dto;

import com.apajaro.platform.domain.entity.SecurityGroup;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
public class SecurityGroupDto {
    private String id;
    private String name;
    private String description;

    @Builder.Default
    private Set<String> permissions = new HashSet<>();

    public static SecurityGroupDto from(SecurityGroup securityGroup) {
        return SecurityGroupDto.builder()
                .id(securityGroup.getId().getValue())
                .name(securityGroup.getName())
                .description(securityGroup.getDescription())
                .permissions(securityGroup.getPermissions().stream().map(ID::getValue).collect(Collectors.toSet()))
                .build();
    }

    public SecurityGroup toDomain() {
        ID finalId = Optional.ofNullable(id).map(ID::of).orElse(null);
        String finalName = Optional.ofNullable(name).orElse("");
        String finalDescription = Optional.ofNullable(this.description).orElse("");

        Set<ID> finalPermissions = Optional.ofNullable(permissions).orElse(new HashSet<>())
                .stream()
                .map(ID::of)
                .collect(Collectors.toSet());

        return SecurityGroup.builder()
                .id(finalId)
                .name(finalName)
                .description(finalDescription)
                .permissions(finalPermissions)
                .build();
    }
}