package com.apajaro.platform.application.repository;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.domain.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    Optional<Person> findById(String id, String organizationId);
    Person create(Person person, String organizationId);
    Person update(Person person, String organizationId);
    Page<Person> findAll(Search search, String organizationId);
    List<Person> findAllLite(Search search, String organizationId);
    void delete(String id, String organizationId);
}
