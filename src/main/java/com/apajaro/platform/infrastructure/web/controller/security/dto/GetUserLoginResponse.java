package com.apajaro.platform.infrastructure.web.controller.security.dto;

import com.apajaro.platform.domain.entity.SecurityGroup;
import com.apajaro.platform.domain.entity.UserLogin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class GetUserLoginResponse {
    private String id;
    private String personId;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    @Builder.Default
    private List<String> securityGroups = List.of();

    public static GetUserLoginResponse from(UserLogin userLogin) {
        List<String> securityGroups = userLogin
                .getSecurityGroups()
                .stream()
                .map(SecurityGroup::getName)
                .toList();

        return GetUserLoginResponse.builder()
                .id(userLogin.getId().getValue())
                .personId(userLogin.getPerson().getId().getValue())
                .firstName(userLogin.getPerson().getFirstName())
                .lastName(userLogin.getPerson().getLastName())
                .enabled(userLogin.isEnabled())
                .securityGroups(securityGroups)
                .build();
    }
}