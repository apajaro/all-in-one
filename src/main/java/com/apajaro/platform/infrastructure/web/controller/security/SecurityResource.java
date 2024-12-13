package com.apajaro.platform.infrastructure.web.controller.security;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.infrastructure.web.CurrentUser;
import com.apajaro.platform.infrastructure.web.UserPrincipal;
import com.apajaro.platform.infrastructure.web.controller.security.dto.SecurityGroupDto;
import com.apajaro.platform.infrastructure.web.controller.security.dto.SecurityPermissionDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/security")
public interface SecurityResource {
    @GetMapping(value = {"/permissions" })
    @PreAuthorize("hasAnyAuthority('roles.write')")
    Set<SecurityPermissionDto> getSecurityPermissions(
            @CurrentUser UserPrincipal userPrincipal
    );

    @GetMapping(value = {"/groups" })
    @PreAuthorize("hasAnyAuthority('roles.write')")
    Page<SecurityGroupDto> getSecurityGroups(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String[] sort,
            @RequestParam(required = false) String direction,
            @CurrentUser UserPrincipal userPrincipal
    );

    @PostMapping(value = {"/groups" })
    @PreAuthorize("hasAnyAuthority('roles.write')")
    SecurityGroupDto createSecurityGroup(
            @RequestBody SecurityGroupDto securityGroupDto,
            @CurrentUser UserPrincipal userPrincipal
    );

    @PutMapping(value = {"/groups/{id}" })
    @PreAuthorize("hasAnyAuthority('roles.write')")
    SecurityGroupDto updateSecurityGroup(
            @PathVariable String id,
            @RequestBody SecurityGroupDto securityGroupDto,
            @CurrentUser UserPrincipal userPrincipal
    );

    @DeleteMapping(value = {"/groups/{id}" })
    @PreAuthorize("hasAnyAuthority('roles.write')")
    void deleteSecurityGroup(
            @PathVariable String id,
            @CurrentUser UserPrincipal userPrincipal
    );
}
