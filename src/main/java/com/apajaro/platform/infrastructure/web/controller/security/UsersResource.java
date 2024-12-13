package com.apajaro.platform.infrastructure.web.controller.security;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.infrastructure.web.CurrentUser;
import com.apajaro.platform.infrastructure.web.UserPrincipal;
import com.apajaro.platform.infrastructure.web.controller.ApiResponse;
import com.apajaro.platform.infrastructure.web.controller.common.dto.FilterAndPaginationRequest;
import com.apajaro.platform.infrastructure.web.controller.security.dto.PatchUserLoginDto;
import com.apajaro.platform.infrastructure.web.controller.security.dto.PostUserLoginDto;
import com.apajaro.platform.infrastructure.web.controller.security.dto.GetUserLoginsResponse;
import com.apajaro.platform.infrastructure.web.controller.security.dto.UserPersonDto;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security/users")
public interface UsersResource {
    @GetMapping
    @PreAuthorize("hasAnyAuthority('users.write')")
    Page<GetUserLoginsResponse> getUserLogins(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String[] sort,
            @RequestParam(required = false) String direction,
            @CurrentUser UserPrincipal userPrincipal
    );

    @GetMapping(value = {"/{id}" })
    @PreAuthorize("hasAnyAuthority('users.write')")
    GetUserLoginsResponse getUserLogin(
            @PathVariable String id,
            @CurrentUser UserPrincipal userPrincipal
    );

    @PostMapping
    @PreAuthorize("hasAnyAuthority('users.write')")
    GetUserLoginsResponse createUserLogin(
            @RequestBody PostUserLoginDto postUserLoginDto,
            @CurrentUser UserPrincipal userPrincipal
    );

    @PatchMapping(value = {"/{id}" })
    @PreAuthorize("hasAnyAuthority('users.write')")
    ApiResponse updateUserLogin(
            @PathVariable String id,
            @Valid @RequestBody PatchUserLoginDto patchUserLoginDto,
            @CurrentUser UserPrincipal userPrincipal
    );

    @DeleteMapping(value = {"/{id}" })
    @PreAuthorize("hasAnyAuthority('users.write')")
    void deleteUserLogin(
            @PathVariable String id,
            @CurrentUser UserPrincipal userPrincipal
    );

    @PostMapping("/people")
    @PreAuthorize("hasAnyAuthority('users.write')")
    List<UserPersonDto> getPeople(@Valid @RequestBody FilterAndPaginationRequest request, @CurrentUser UserPrincipal userPrincipal);
}
