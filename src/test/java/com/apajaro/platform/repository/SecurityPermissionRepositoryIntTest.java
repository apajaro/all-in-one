package com.apajaro.platform.repository;

import com.apajaro.platform.common.JdbcIntTest;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "classpath:db/changelog/v1/test-seed-data.sql")
public class SecurityPermissionRepositoryIntTest extends JdbcIntTest {
    /*@Autowired
    SecurityPermissionRepository securityPermissionRepository;

    @Test
    void findAllReturnsAllSecurityPermissions() {
        Set<SecurityPermission> securityPermissions = securityPermissionRepository.findAll();

        Assertions.assertEquals(5, securityPermissions.size());
    }

    @Test
    void getUserLoginPermissionsReturnsAllPermissionsForUserLogin() {
        Set<SecurityPermission> securityPermissions = securityPermissionRepository.getUserLoginPermissions("e4382a6c-f173-450b-b072-3a30ce4f953e");

        Assertions.assertEquals(5, securityPermissions.size());
    }*/
}