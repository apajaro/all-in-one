package com.apajaro.platform.application.repository;

import com.apajaro.platform.domain.entity.UserLoginToken;

import java.util.Optional;

public interface UserLoginTokenRepository {

    UserLoginToken create(UserLoginToken userLogin);
    UserLoginToken update(UserLoginToken userLogin);

    Optional<UserLoginToken> findByTokenAndUserLogin(String token, String userLoginId);
}
