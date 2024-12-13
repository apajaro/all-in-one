package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.application.repository.SecurityPermissionRepository;
import com.apajaro.platform.domain.entity.SecurityPermission;
import com.apajaro.platform.infrastructure.data.entity.SecurityPermissionDao;
import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class SecurityPermissionRepositoryImpl implements SecurityPermissionRepository {
    private final SecurityPermissionRepositoryJdbc repository;
    private final JdbcAggregateTemplate template;

    @Override
    public Set<SecurityPermission> findAll() {
        return repository
                .findAll()
                .stream()
                .map(SecurityPermissionDao::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<SecurityPermission> getUserLoginPermissions(String userLoginId) {
        return repository.getUserLoginPermissions(userLoginId)
                .stream()
                .map(SecurityPermissionDao::toDomain)
                .collect(Collectors.toSet());
    }
}
