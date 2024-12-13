package com.apajaro.platform.repository;

import com.apajaro.platform.application.repository.PersonRepository;
import com.apajaro.platform.application.repository.SecurityGroupRepository;
import com.apajaro.platform.application.repository.SecurityPermissionRepository;
import com.apajaro.platform.application.repository.UserLoginRepository;
import com.apajaro.platform.common.JdbcIntTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "classpath:db/changelog/v1/test-seed-data.sql")
public class UserLoginRepositoryIntTest extends JdbcIntTest {
    @Autowired
    UserLoginRepository userLoginRepository;
    @Autowired
    SecurityPermissionRepository securityPermissionRepository;
    @Autowired
    SecurityGroupRepository securityGroupRepository;
    @Autowired
    PersonRepository personRepository;


    /*void userLoginCreationSucceeds() {
        String organizationId = "c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29";

        Person person = TestUtils.makePerson();
        personRepository.create(person, organizationId);

        UserLogin userLogin = TestUtils.makeUserLogin();
        userLogin.setPerson(person);
        UserLogin createdUserLogin = userLoginRepository.create(userLogin);

        Assertions.assertNotNull(createdUserLogin);
        Assertions.assertEquals(person.getId(), createdUserLogin.getPerson().getId());
    }

    @Test
    void userLoginUpdateSucceeds() {
        String organizationId = "c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29";

        Person person = TestUtils.makePerson();
        personRepository.create(person, organizationId);

        UserLogin userLogin = TestUtils.makeUserLogin();
        userLogin.setPerson(person);
        UserLogin createdUserLogin = userLoginRepository.create(userLogin);

        Assertions.assertNotNull(createdUserLogin);
        Assertions.assertEquals(person.getId(), createdUserLogin.getPerson().getId());

        createdUserLogin.setEnabled(false);
        createdUserLogin.setSuccessiveFailedLogins(3);
        createdUserLogin.setDisabledDateTime(Instant.now().plus(Duration.ofMinutes(5)));
        UserLogin updatedUserLogin = userLoginRepository.update(createdUserLogin);

        Assertions.assertNotNull(updatedUserLogin);
        Assertions.assertFalse(updatedUserLogin.isEnabled());
        Assertions.assertEquals(3, updatedUserLogin.getSuccessiveFailedLogins());
        Assertions.assertNotNull(updatedUserLogin.getDisabledDateTime());
    }

    @Test
    void userLoginFindByUsernameSucceeds() {
        UserLogin foundUserLogin = userLoginRepository.findByUsername("111222").orElse(null);

        Assertions.assertNotNull(foundUserLogin);
        Assertions.assertEquals("e4382a6c-f173-450b-b072-3a30ce4f953e", foundUserLogin.getId().getValue());
    }

    @Test
    void userLoginFindByIdSucceeds() {
        UserLogin foundUserLogin = userLoginRepository.findById("e4382a6c-f173-450b-b072-3a30ce4f953e").orElse(null);

        Assertions.assertNotNull(foundUserLogin);
        Assertions.assertEquals("111222", foundUserLogin.getUsername());
    }*/
}
