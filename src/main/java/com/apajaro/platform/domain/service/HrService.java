package com.apajaro.platform.domain.service;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.domain.entity.Person;
import com.apajaro.platform.domain.entity.PostalAddress;
import com.apajaro.platform.domain.entity.TelecomNumber;

import java.util.List;

public interface HrService {
    Page<Person> findAllPeople(Search search, String organizationId);
    List<Person> findAllPeopleLite(Search search, String organizationId);
    Person createPerson(Person person, String organizationId);
    Person updatePerson(Person person, String organizationId);
    void deletePerson(String personId, String organizationId);
    TelecomNumber getPersonTelecomNumber(String personId, String organizationId);
    PostalAddress getPersonPostalAddress(String personId, String organizationId);
}
