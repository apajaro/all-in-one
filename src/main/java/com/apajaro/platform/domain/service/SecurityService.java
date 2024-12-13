package com.apajaro.platform.domain.service;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.domain.entity.Person;
import com.apajaro.platform.domain.entity.SecurityGroup;
import com.apajaro.platform.domain.entity.SecurityPermission;
import com.apajaro.platform.domain.entity.UserLogin;

import java.util.List;
import java.util.Set;

public interface SecurityService {
    Set<SecurityPermission> getSecurityPermissions();
    Page<SecurityGroup> findAllSecurityGroups(Search search, String organizationId);
    SecurityGroup createSecurityGroup(SecurityGroup securityGroup, String organizationId);
    SecurityGroup updateSecurityGroup(SecurityGroup securityGroup, String organizationId);
    void deleteSecurityGroup(String securityGroupId, String organizationId);
    Page<UserLogin> getUserLogins(Search search, String organizationId);

    UserLogin getUserLogin(String userLoginId, String organizationId);
    UserLogin createUserLogin(UserLogin userLogin, String organizationId);
    UserLogin updateUserLogin(String userLoginId, List<String> securityGroups, String organizationId);
    void deleteUserLogin(String userLoginId, String organizationId);
    List<Person> findPeople(Search search, String organizationId);
}
