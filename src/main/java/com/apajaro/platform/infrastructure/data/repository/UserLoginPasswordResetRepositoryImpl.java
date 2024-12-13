package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.application.repository.UserLoginPasswordResetRepository;
import com.apajaro.platform.domain.entity.UserLoginPasswordReset;
import com.apajaro.platform.infrastructure.data.entity.UserLoginPasswordResetDao;
import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserLoginPasswordResetRepositoryImpl implements UserLoginPasswordResetRepository {
    private final UserLoginPasswordResetRepositoryJdbc repository;
    private final JdbcAggregateTemplate template;

    @Override
    public UserLoginPasswordReset create(UserLoginPasswordReset userLoginPasswordReset) {
        return template.insert(UserLoginPasswordResetDao.fromDomain(userLoginPasswordReset)).toDomain();
    }

    @Override
    public UserLoginPasswordReset update(UserLoginPasswordReset userLoginPasswordReset) {
        return template.update(UserLoginPasswordResetDao.fromDomain(userLoginPasswordReset)).toDomain();
    }

    @Override
    public Optional<UserLoginPasswordReset> findByToken(String token) {
        return repository.findByToken(token).map(UserLoginPasswordResetDao::toDomain);
    }
}
