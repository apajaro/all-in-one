package com.apajaro.platform.infrastructure.web.controller.security;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.domain.entity.SecurityGroup;
import com.apajaro.platform.domain.entity.SecurityPermission;
import com.apajaro.platform.domain.service.SecurityService;
import com.apajaro.platform.infrastructure.web.UserPrincipal;
import com.apajaro.platform.infrastructure.web.controller.security.dto.SecurityGroupDto;
import com.apajaro.platform.infrastructure.web.controller.security.dto.SecurityPermissionDto;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class SecurityController implements SecurityResource {
    private final SecurityService securityService;

    @Override
    public Set<SecurityPermissionDto> getSecurityPermissions(UserPrincipal userPrincipal) {
        Set<SecurityPermission> permissions = securityService.getSecurityPermissions();

        return permissions.stream().map(SecurityPermissionDto::from).collect(Collectors.toSet());
    }

    @Override
    public Page<SecurityGroupDto> getSecurityGroups(String query, Integer page, Integer size, String[] sort, String direction, UserPrincipal userPrincipal) {
        Search search = Search.of(query, page, size, sort, direction);

        Page<SecurityGroup> securityGroups = securityService.findAllSecurityGroups(search, userPrincipal.getOrganizationId());

        return Page.<SecurityGroupDto>builder()
                .content(securityGroups.getContent().stream().map(SecurityGroupDto::from).toList())
                .totalPages(securityGroups.getTotalPages())
                .totalElements(securityGroups.getTotalElements())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SecurityGroupDto createSecurityGroup(SecurityGroupDto securityGroupDto, UserPrincipal userPrincipal) {
        SecurityGroup securityGroup = securityService.createSecurityGroup(securityGroupDto.toDomain(), userPrincipal.getOrganizationId());

        return SecurityGroupDto.from(securityGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SecurityGroupDto updateSecurityGroup(String id, SecurityGroupDto securityGroupDto, UserPrincipal userPrincipal) {
        securityGroupDto.setId(id);
        SecurityGroup securityGroup = securityService.updateSecurityGroup(securityGroupDto.toDomain(), userPrincipal.getOrganizationId());

        return SecurityGroupDto.from(securityGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSecurityGroup(String id, UserPrincipal userPrincipal) {
        securityService.deleteSecurityGroup(id, userPrincipal.getOrganizationId());
    }
}
