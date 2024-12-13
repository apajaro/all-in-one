package com.apajaro.platform.application.service;

import com.apajaro.platform.application.EmailService;
import com.apajaro.platform.application.PasswordEncoder;
import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.application.repository.*;
import com.apajaro.platform.domain.entity.*;
import com.apajaro.platform.application.repository.*;
import com.apajaro.platform.domain.entity.*;
import com.apajaro.platform.domain.exception.DomainException;
import com.apajaro.platform.domain.exception.ErrorCode;
import com.apajaro.platform.domain.service.SecurityService;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private final SecurityPermissionRepository securityPermissionRepository;
    private final SecurityGroupRepository securityGroupRepository;
    private final UserLoginRepository userLoginRepository;
    private final PersonRepository personRepository;
    private final UserLoginPasswordResetRepository userLoginPasswordResetRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Set<SecurityPermission> getSecurityPermissions() {
        return securityPermissionRepository.findAll();
    }

    @Override
    public Page<SecurityGroup> findAllSecurityGroups(Search search, String organizationId) {
        return securityGroupRepository.findAll(organizationId, search);
    }

    @Override
    public SecurityGroup createSecurityGroup(SecurityGroup securityGroup, String organizationId) {
        securityGroup.setId(ID.generate());

        return securityGroupRepository.create(securityGroup, organizationId);
    }

    @Override
    public SecurityGroup updateSecurityGroup(SecurityGroup securityGroup, String organizationId) {
        return securityGroupRepository.update(securityGroup, organizationId);
    }

    @Override
    public void deleteSecurityGroup(String securityGroupId, String organizationId) {
        securityGroupRepository.delete(securityGroupId, organizationId);
    }

    @Override
    public Page<UserLogin> getUserLogins(Search search, String organizationId) {
        return userLoginRepository.findAll(search, organizationId);
    }

    @Override
    public UserLogin getUserLogin(String userLoginId, String organizationId) {
        return userLoginRepository.findById(userLoginId, organizationId).orElseThrow(() -> new DomainException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public UserLogin createUserLogin(UserLogin userLogin, String organizationId) {
        Person person = personRepository.findById(userLogin.getPerson().getId().getValue(), organizationId)
                .orElseThrow(() -> new DomainException(ErrorCode.PERSON_NOT_FOUND));

        if (!person.canCreateUserLogin()) {
            Person userLoginPerson = userLogin.getPerson();
            person.setDataForUserLogin(
                    userLoginPerson.getIdentificationType(),
                    userLoginPerson.getIdentification(),
                    userLoginPerson.getEmailAddress()
            );

            person = personRepository.update(person, organizationId);
        }

        userLogin.setId(ID.generate());

        userLogin.setDefaultValues();
        userLogin.setUsername(person.getIdentification());
        userLogin.setEmailAddress(person.getEmailAddress());
        userLogin.setPassword(passwordEncoder.encode(userLogin.getEmailAddress().getValue()));
        userLogin.setOrganizationId(ID.of(organizationId));

        UserLogin createdUserLogin = userLoginRepository.create(userLogin, organizationId);

        Long validForSeconds = 604800L;
        UserLoginPasswordReset userLoginPasswordReset = UserLoginPasswordReset.createForUser(userLogin.getId(), validForSeconds);
        userLoginPasswordResetRepository.create(userLoginPasswordReset);

        Map<String, Object> data = Map.of(
                "firstName", person.getFirstName(),
                "lastName", person.getLastName(),
                "username", createdUserLogin.getUsername(),
                "resetUrl", "http://localhost:5173/reset-password?token=" + userLoginPasswordReset.getToken().getValue(),
                "validForSeconds", validForSeconds,
                "signature", "AllinOne"
        );

        emailService.sendEmail(userLogin.getEmailAddress().getValue(),
                "Cuenta creada",
                "no-reply@allinone.com",
                "AllinOne",
                data, "createdAccount");

        return createdUserLogin;
    }

    @Override
    public UserLogin updateUserLogin(String userLoginId, List<String> securityGroups, String organizationId) {
        UserLogin userLogin = userLoginRepository.findById(userLoginId, organizationId)
                .orElseThrow(() -> new DomainException(ErrorCode.USER_NOT_FOUND));

        userLogin.setSecurityGroups(new HashSet<>());
        securityGroups.forEach(userLogin::addSecurityGroup);

        return userLoginRepository.update(userLogin, organizationId);
    }

    @Override
    public void deleteUserLogin(String userLoginId, String organizationId) {
        userLoginRepository.delete(userLoginId, organizationId);
    }

    @Override
    public List<Person> findPeople(Search search, String organizationId) {
        return userLoginRepository.findPeople(organizationId, search);
    }
}
