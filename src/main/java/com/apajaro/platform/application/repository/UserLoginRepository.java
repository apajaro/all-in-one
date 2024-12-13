package com.apajaro.platform.application.repository;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.domain.entity.Person;
import com.apajaro.platform.domain.entity.UserLogin;

import java.util.List;
import java.util.Optional;

public interface UserLoginRepository {
    Optional<UserLogin> findByUsername(String username);

    UserLogin create(UserLogin userLogin, String organizationId);
    UserLogin update(UserLogin userLogin, String organizationId);
    void delete(String userLoginId, String organizationId);

    Optional<UserLogin> findById(String id);
    Optional<UserLogin> findById(String id, String organizationId);

    Optional<UserLogin> findByEmail(String email);
    Page<UserLogin> findAll(Search search, String organizationId);
    List<Person> findPeople(String organizationId, Search search);
}
