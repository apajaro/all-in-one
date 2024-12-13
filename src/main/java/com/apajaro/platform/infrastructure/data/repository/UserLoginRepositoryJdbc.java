package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.infrastructure.data.dto.PersonDTO;
import com.apajaro.platform.infrastructure.data.dto.UserLoginPersonDTO;
import com.apajaro.platform.infrastructure.data.entity.UserLoginDao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserLoginRepositoryJdbc extends ListCrudRepository<UserLoginDao, String>, PagingAndSortingRepository<UserLoginDao, String> {
    Optional<UserLoginDao> findByUsername(String username);
    Optional<UserLoginDao> findByEmailAddress(String emailAddress);
    long countByOrganizationId(String organizationId);

    Optional<UserLoginDao> findByIdAndOrganizationId(String id, String organizationId);

    @Query("SELECT ul.id, ul.enabled, ul.person_id, p.first_name, p.last_name FROM user_login ul JOIN person p ON ul.person_id = p.id WHERE ul.organization_id = :organizationId group by ul.id, p.first_name, p.last_name ")
    List<UserLoginPersonDTO> findByOrganizationIdCustom(@Param("organizationId") String organizationId, Pageable pageable);

    @Query("SELECT ul.*, p.first_name, p.last_name, JSON_BUILD_OBJECT('security_groups', JSON_AGG(JSON_BUILD_OBJECT('id', sg.id, 'name', sg.\"name\" ))) as security_groups FROM user_login ul JOIN person p ON ul.person_id = p.id left join user_login_security_group ulsg on ulsg.user_login_id = ul.id left join security_group sg on sg.id = ulsg.security_group_id WHERE ul.organization_id = :organizationId AND ul.id = :id GROUP BY ul.id, p.first_name, p.last_name")
    Optional<UserLoginPersonDTO> findByIdAndOrganizationIdCustom(@Param("id") String id, @Param("organizationId") String organizationId);

    @Query("SELECT ul.id, ul.enabled, ul.person_id, p.first_name, p.last_name FROM user_login ul JOIN person p ON ul.person_id = p.id WHERE ul.organization_id = :organizationId AND ul.id = :id")
    List<PersonDTO> findPeopleByOrganizationId(@Param("organizationId") String organizationId);
}
