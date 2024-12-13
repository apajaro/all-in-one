package com.apajaro.platform.repository;

import com.apajaro.platform.common.JdbcIntTest;

public class PersonRepositoryIntTest extends JdbcIntTest {
    /*@Autowired
    PersonRepository personRepository;
    @Autowired
    OrganizationRepository organizationRepository;

    @Test
    void personCreationSucceeds() {
        Organization organization = TestUtils.makeOrganization();
        organizationRepository.create(organization);

        Person person = TestUtils.makePerson();
        Person createdPerson = personRepository.create(person, organization.getId().getValue());

        Assertions.assertNotNull(createdPerson);
    }

    @Test
    void personUpdateSucceeds() {
        Organization organization = TestUtils.makeOrganization();
        organizationRepository.create(organization);

        Person person = TestUtils.makePerson();
        Person createdPerson = personRepository.create(person, organization.getId().getValue());
        createdPerson.setFirstName("Updated Person");
        Person updatedPerson = personRepository.update(createdPerson, organization.getId().getValue());

        Assertions.assertNotNull(updatedPerson);
        Assertions.assertEquals("Updated Person", updatedPerson.getFirstName());
    }

    @Test
    void personFindByIdSucceeds() {
        Organization organization = TestUtils.makeOrganization();
        organizationRepository.create(organization);

        Person person = TestUtils.makePerson();
        Person createdPerson = personRepository.create(person, organization.getId().getValue());
        Person foundPerson = personRepository.findById(createdPerson.getId().getValue(), organization.getId().getValue()).orElse(null);

        Assertions.assertNotNull(foundPerson);
    }

    @Test
    void personFindByIdFails() {
        Person foundPerson = personRepository.findById("invalid-id", ID.generate().getValue()).orElse(null);

        Assertions.assertNull(foundPerson);
    }*/
}
