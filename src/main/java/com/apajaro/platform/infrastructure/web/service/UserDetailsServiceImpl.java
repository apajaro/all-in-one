package com.apajaro.platform.infrastructure.web.service;

import com.apajaro.platform.application.repository.SecurityPermissionRepository;
import com.apajaro.platform.application.repository.UserLoginRepository;
import com.apajaro.platform.domain.entity.SecurityPermission;
import com.apajaro.platform.domain.entity.UserLogin;
import com.apajaro.platform.domain.exception.ErrorCode;
import com.apajaro.platform.domain.exception.NotFoundException;
import com.apajaro.platform.infrastructure.web.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserLoginRepository userLoginRepository;
    private SecurityPermissionRepository securityPermissionRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin userLogin = userLoginRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Set<SecurityPermission> permissions = securityPermissionRepository
                .getUserLoginPermissions(userLogin.getId().getValue());
        return new User(
                userLogin.getUsername(),
                userLogin.getPassword(),
                UserPrincipal.getAuthoritiesFromDomain(permissions)
        );
    }

    @Transactional
    public UserDetails loadUserById(String id) {
        UserLogin userLogin = userLoginRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Set<SecurityPermission> permissions = securityPermissionRepository
                .getUserLoginPermissions(userLogin.getId().getValue());
        return UserPrincipal.from(userLogin, permissions);
    }
}
