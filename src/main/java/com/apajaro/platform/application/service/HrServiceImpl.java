package com.apajaro.platform.application.service;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.application.repository.PersonRepository;
import com.apajaro.platform.domain.entity.Person;
import com.apajaro.platform.domain.service.HrService;
import com.apajaro.platform.domain.valueobject.ID;
import com.apajaro.platform.domain.entity.PostalAddress;
import com.apajaro.platform.domain.entity.TelecomNumber;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class HrServiceImpl implements HrService {
    private final PersonRepository personRepository;

    @Override
    public Page<Person> findAllPeople(Search search, String organizationId) {
        return personRepository.findAll(search, organizationId);
    }

    @Override
    public List<Person> findAllPeopleLite(Search search, String organizationId) {
        return personRepository.findAllLite(search, organizationId);
    }

    @Override
    public Person createPerson(Person person, String organizationId) {
        person.setId(ID.generate());

        return personRepository.create(person, organizationId);
    }

    @Override
    public Person updatePerson(Person person, String organizationId) {
        return personRepository.update(person, organizationId);
    }

    @Override
    public void deletePerson(String personId, String organizationId) {
        personRepository.delete(personId, organizationId);
    }

    @Override
    public TelecomNumber getPersonTelecomNumber(String personId, String organizationId) {
        return null;
    }

    @Override
    public PostalAddress getPersonPostalAddress(String personId, String organizationId) {
        return null;
    }
}
