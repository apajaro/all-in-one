package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.infrastructure.data.entity.UserLoginTokenDao;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserLoginTokenRepositoryJdbc extends ListCrudRepository<UserLoginTokenDao, String> {
    Optional<UserLoginTokenDao> findByTokenAndUserLoginId(String token, String userLoginId);
}
