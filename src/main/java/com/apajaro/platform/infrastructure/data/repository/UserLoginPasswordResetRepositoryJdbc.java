package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.infrastructure.data.entity.UserLoginPasswordResetDao;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserLoginPasswordResetRepositoryJdbc extends ListCrudRepository<UserLoginPasswordResetDao, String> {
    Optional<UserLoginPasswordResetDao> findByToken(String token);
}
