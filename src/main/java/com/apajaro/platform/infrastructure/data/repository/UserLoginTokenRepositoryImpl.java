package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.application.repository.UserLoginTokenRepository;
import com.apajaro.platform.domain.entity.UserLoginToken;
import com.apajaro.platform.infrastructure.data.entity.UserLoginTokenDao;
import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserLoginTokenRepositoryImpl implements UserLoginTokenRepository {
    private final UserLoginTokenRepositoryJdbc repository;
    private final JdbcAggregateTemplate template;

    @Override
    public UserLoginToken create(UserLoginToken userLogin) {
        return template.insert(UserLoginTokenDao.fromDomain(userLogin)).toDomain();
    }

    @Override
    public UserLoginToken update(UserLoginToken userLogin) {
        return template.update(UserLoginTokenDao.fromDomain(userLogin)).toDomain();
    }

    @Override
    public Optional<UserLoginToken> findByTokenAndUserLogin(String token, String userLoginId) {
        return repository.findByTokenAndUserLoginId(token, userLoginId).map(UserLoginTokenDao::toDomain);
    }
}
