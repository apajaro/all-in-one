package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.application.repository.OrganizationRepository;
import com.apajaro.platform.domain.entity.Organization;
import com.apajaro.platform.infrastructure.data.entity.OrganizationDao;
import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrganizationRepositoryImpl implements OrganizationRepository {
    private final OrganizationRepositoryJdbc repository;
    private final JdbcAggregateTemplate template;

    @Override
    public Optional<Organization> findById(String organizationId) {
        return repository.findById(organizationId).map(OrganizationDao::toDomain);
    }

    @Override
    public Organization create(Organization organization) {
        return template.insert(OrganizationDao.fromDomain(organization)).toDomain();
    }

    @Override
    public Organization update(Organization organization) {
        return template.update(OrganizationDao.fromDomain(organization)).toDomain();
    }
}
