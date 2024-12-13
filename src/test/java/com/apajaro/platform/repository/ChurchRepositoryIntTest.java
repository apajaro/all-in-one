package com.apajaro.platform.repository;

import com.apajaro.platform.common.JdbcIntTest;

public class OrganizationRepositoryIntTest extends JdbcIntTest {
    /*@Autowired
    OrganizationRepository organizationRepository;

    @Test
    void organizationCreationSucceeds() {
        Organization organization = TestUtils.makeOrganization();
        Organization createdOrganization = organizationRepository.create(organization);

        Assertions.assertNotNull(createdOrganization);
    }

    @Test
    void organizationUpdateSucceeds() {
        Organization organization = TestUtils.makeOrganization();
        Organization createdOrganization = organizationRepository.create(organization);
        createdOrganization.setName("Updated Organization");
        Organization updatedOrganization = organizationRepository.update(createdOrganization);

        Assertions.assertNotNull(updatedOrganization);
        Assertions.assertEquals("Updated Organization", updatedOrganization.getName());
    }

    @Test
    void organizationFindByIdSucceeds() {
        Organization organization = TestUtils.makeOrganization();
        Organization createdOrganization = organizationRepository.create(organization);
        Organization foundOrganization = organizationRepository.findById(createdOrganization.getId().getValue()).orElse(null);

        Assertions.assertNotNull(foundOrganization);
    }

    @Test
    void organizationFindByIdFails() {
        Organization foundOrganization = organizationRepository.findById("invalid-id").orElse(null);

        Assertions.assertNull(foundOrganization);
    }*/
}
