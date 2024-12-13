package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.application.repository.SecurityGroupRepository;
import com.apajaro.platform.domain.entity.SecurityGroup;
import com.apajaro.platform.infrastructure.data.PaginationUtils;
import com.apajaro.platform.infrastructure.data.entity.SecurityGroupDao;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SecurityGroupRepositoryImpl implements SecurityGroupRepository {
    private final SecurityGroupRepositoryJdbc repository;
    private final JdbcAggregateTemplate template;

    @Override
    public Page<SecurityGroup> findAll(String organizationId, Search search) {
        Pageable pageable = PaginationUtils.toPageable(search.getPagination());
        var pagedResult = repository.findByNameContainingIgnoreCaseAndOrganizationId(search.getQuery(), organizationId, pageable);
        List<SecurityGroup> securityGroups = pagedResult.getContent().stream().map(SecurityGroupDao::toDomain).toList();

        return Page.<SecurityGroup>builder()
                .content(securityGroups)
                .totalElements(pagedResult.getTotalElements())
                .totalPages(pagedResult.getTotalPages())
                .build();
    }

    @Override
    public Optional<SecurityGroup> findById(String id, String organizationId) {
        return repository.findByIdAndOrganizationId(id, organizationId).map(SecurityGroupDao::toDomain);
    }

    @Override
    public SecurityGroup create(SecurityGroup securityGroup, String organizationId) {
        return template.insert(SecurityGroupDao.fromDomain(securityGroup, organizationId)).toDomain();
    }

    @Override
    public SecurityGroup update(SecurityGroup securityGroup, String organizationId) {
        return template.update(SecurityGroupDao.fromDomain(securityGroup, organizationId)).toDomain();
    }

    @Override
    public void delete(String id, String organizationId) {
        repository.deleteByIdAndOrganizationId(id, organizationId);
    }
}
