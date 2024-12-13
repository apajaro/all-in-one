package com.apajaro.platform.infrastructure.web.controller.security;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.domain.entity.UserLogin;
import com.apajaro.platform.domain.service.SecurityService;
import com.apajaro.platform.infrastructure.web.UserPrincipal;
import com.apajaro.platform.infrastructure.web.controller.ApiResponse;
import com.apajaro.platform.infrastructure.web.controller.common.dto.FilterAndPaginationRequest;
import com.apajaro.platform.infrastructure.web.controller.security.dto.GetUserLoginsResponse;
import com.apajaro.platform.infrastructure.web.controller.security.dto.PatchUserLoginDto;
import com.apajaro.platform.infrastructure.web.controller.security.dto.PostUserLoginDto;
import com.apajaro.platform.infrastructure.web.controller.security.dto.UserPersonDto;
import com.apajaro.platform.infrastructure.web.controller.security.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UsersController implements UsersResource {
    private final SecurityService securityService;

    @Override
    public Page<GetUserLoginsResponse> getUserLogins(String query, Integer page, Integer size, String[] sort, String direction, UserPrincipal userPrincipal) {
        Search search = Search.of(query, page, size, sort, direction);
        Page<UserLogin> data = securityService.getUserLogins(search, userPrincipal.getOrganizationId());
        return Page.<GetUserLoginsResponse>builder()
                .content(data.getContent().stream().map(GetUserLoginsResponse::from).toList())
                .totalPages(data.getTotalPages())
                .totalElements(data.getTotalElements())
                .build();
    }

    @Override
    public GetUserLoginsResponse getUserLogin(String id, UserPrincipal userPrincipal) {
        return GetUserLoginsResponse.from(securityService.getUserLogin(id, userPrincipal.getOrganizationId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GetUserLoginsResponse createUserLogin(PostUserLoginDto postUserLoginDto, UserPrincipal userPrincipal) {
        return GetUserLoginsResponse.from(
                securityService.createUserLogin(postUserLoginDto.toDomain(), userPrincipal.getOrganizationId())
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse updateUserLogin(String id, PatchUserLoginDto patchUserLoginDto, UserPrincipal userPrincipal) {
        securityService.updateUserLogin(id, patchUserLoginDto.getSecurityGroups(), userPrincipal.getOrganizationId());
        return new ApiResponse(true, "User updated successfully");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserLogin(String id, UserPrincipal userPrincipal) {

    }

    @Override
    public List<UserPersonDto> getPeople(FilterAndPaginationRequest request, UserPrincipal userPrincipal) {
        Search search = Search.of(request.getSearchTerm(), request.getPage(), request.getSize(), request.getSort());
        return securityService.findPeople(search, userPrincipal.getOrganizationId()).stream()
                .map(UserPersonDto::from)
                .toList();
    }
}
