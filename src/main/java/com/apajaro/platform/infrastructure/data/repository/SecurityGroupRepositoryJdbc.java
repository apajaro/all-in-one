package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.infrastructure.data.entity.SecurityGroupDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SecurityGroupRepositoryJdbc extends ListCrudRepository<SecurityGroupDao, String>, PagingAndSortingRepository<SecurityGroupDao, String> {
    Page<SecurityGroupDao> findByNameContainingIgnoreCaseAndOrganizationId(String query, String organizationId, Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM security_group WHERE id = :id AND organization_id = :organizationId")
    void deleteByIdAndOrganizationId(@Param("id") String id, @Param("organizationId") String organizationId);
    Optional<SecurityGroupDao> findByIdAndOrganizationId(String id, String organizationId);
}
