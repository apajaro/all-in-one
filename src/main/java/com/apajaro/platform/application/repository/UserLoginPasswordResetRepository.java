package com.apajaro.platform.application.repository;

import com.apajaro.platform.domain.entity.UserLoginPasswordReset;

import java.util.Optional;

public interface UserLoginPasswordResetRepository {

    UserLoginPasswordReset create(UserLoginPasswordReset userLoginPasswordReset);
    UserLoginPasswordReset update(UserLoginPasswordReset userLoginPasswordReset);
    Optional<UserLoginPasswordReset> findByToken(String token);
}
