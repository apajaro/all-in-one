package com.apajaro.platform.domain.service;

import com.apajaro.platform.domain.entity.SecurityPermission;
import com.apajaro.platform.domain.entity.UserLogin;
import com.apajaro.platform.domain.entity.UserLoginToken;
import com.apajaro.platform.domain.valueobject.ID;

import java.util.Optional;
import java.util.Set;

public interface AuthService {
    UserLogin login(String username, String password);
    UserLoginToken createUserLoginToken(String token, ID userLoginId);
    Set<SecurityPermission> getUserSecurityPermissions(ID userLoginId);
    Set<String> getUserPermissionSlugs(ID userLoginId);

    Optional<UserLogin> findUserLoginById(String id);
    Boolean isUserLoginTokenExpired(String token, String userLoginId);
    void expireUserLoginTokenToken(String token, String userLoginId);
    void forgotPassword(String email);
    void resetPassword(String newPassword, String token);
    void verifyResetPasswordToken(String token);
}
