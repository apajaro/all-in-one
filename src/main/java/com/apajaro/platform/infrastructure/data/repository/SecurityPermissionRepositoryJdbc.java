package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.infrastructure.data.entity.SecurityPermissionDao;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface SecurityPermissionRepositoryJdbc extends ListCrudRepository<SecurityPermissionDao, String> {
    @Query("select distinct sp.* FROM security_permission sp LEFT JOIN security_group_permission sgp ON sgp.security_permission_id = sp.id LEFT JOIN security_group sg ON sg.id = sgp.security_group_id LEFT JOIN user_login_security_group ulsg ON ulsg.security_group_id = sg.id WHERE ulsg.user_login_id = :userLoginId")
    Collection<SecurityPermissionDao> getUserLoginPermissions(@Param("userLoginId") String userLoginId);
}
