package com.apajaro.platform.application.repository;

import com.apajaro.platform.domain.entity.ContactMech;

import java.util.List;

public interface ContactMechRepository {
    List<ContactMech> findAllForPerson(String personId);
}
