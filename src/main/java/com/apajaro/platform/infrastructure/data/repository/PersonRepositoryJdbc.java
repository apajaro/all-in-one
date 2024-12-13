package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.infrastructure.data.entity.PersonDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PersonRepositoryJdbc extends ListCrudRepository<PersonDao, String>, PagingAndSortingRepository<PersonDao, String> {
    Page<PersonDao> findByOrganizationId(String organizationId, Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM person WHERE id = :id AND organization_id = :organizationId")
    void deleteByIdAndOrganizationId(@Param("id") String id, @Param("organizationId") String organizationId);

    long countByOrganizationId(String organizationId);
}
