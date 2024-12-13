package com.apajaro.platform.repository;

import com.apajaro.platform.common.JdbcIntTest;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "classpath:db/changelog/v1/test-seed-data.sql")
public class SecurityGroupRepositoryIntTest extends JdbcIntTest {
    /*@Autowired
    SecurityGroupRepository securityGroupRepository;
    @Autowired
    OrganizationRepository organizationRepository;

    @Test
    void securityGroupCreationSucceeds() {
        String organizationId = "c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29";

        SecurityGroup securityGroup = TestUtils.makeSecurityGroup();
        SecurityGroup createdSecurityGroup = securityGroupRepository.create(securityGroup, organizationId);

        Assertions.assertNotNull(createdSecurityGroup);
        Assertions.assertEquals(0, createdSecurityGroup.getPermissions().size());
    }

    @Test
    void securityGroupUpdateSucceeds() {
        String organizationId = "c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29";

        SecurityGroup securityGroup = TestUtils.makeSecurityGroup();
        SecurityGroup createdSecurityGroup = securityGroupRepository.create(securityGroup, organizationId);
        createdSecurityGroup.addSecurityPermission(ID.of("5716a161-752f-4d98-b5ff-2fcab6c77f1a"));
        createdSecurityGroup.addSecurityPermission(ID.of("61d70e1a-0b46-4f93-a937-b73e7c361764"));

        SecurityGroup updatedSecurityGroup = securityGroupRepository.update(createdSecurityGroup, organizationId);

        Assertions.assertNotNull(updatedSecurityGroup);
        Assertions.assertEquals(2, updatedSecurityGroup.getPermissions().size());
    }

    @Test
    void securityGroupDeleteSucceeds() {
        String organizationId = "c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29";

        SecurityGroup securityGroup = TestUtils.makeSecurityGroup();
        SecurityGroup createdSecurityGroup = securityGroupRepository.create(securityGroup, organizationId);
        String securityGroupId = createdSecurityGroup.getId().getValue();

        securityGroupRepository.delete(securityGroupId, organizationId);

        Assertions.assertNull(securityGroupRepository.findById(securityGroupId, organizationId).orElse(null));
    }

    @Test
    void securityGroupFindByIdSucceeds() {
        String organizationId = "c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29";

        SecurityGroup securityGroup = TestUtils.makeSecurityGroup();
        SecurityGroup createdSecurityGroup = securityGroupRepository.create(securityGroup, organizationId);
        String securityGroupId = createdSecurityGroup.getId().getValue();

        SecurityGroup foundSecurityGroup = securityGroupRepository.findById(securityGroupId, organizationId).get();

        Assertions.assertNotNull(foundSecurityGroup);
        Assertions.assertEquals(securityGroupId, foundSecurityGroup.getId().getValue());
    }

    @Test
    void securityGroupFindAllSucceeds() {
        Search search = Search.builder()
                .query("")
                .pagination(Pagination.builder().build())
                .build();

        Page<SecurityGroup> foundSecurityGroups = securityGroupRepository.findAll("c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29", search);

        Assertions.assertNotNull(foundSecurityGroups);
        Assertions.assertEquals("98bcfdf8-076d-451f-9aba-c454ff1426ae", foundSecurityGroups.getContent().get(0).getId().getValue());
        Assertions.assertEquals(1, foundSecurityGroups.getTotalElements());
        Assertions.assertEquals(1, foundSecurityGroups.getTotalPages());
    }*/
}