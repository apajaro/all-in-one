package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.application.repository.ContactMechRepository;
import com.apajaro.platform.domain.entity.ContactMech;
import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ContactMechRepositoryImpl implements ContactMechRepository {
    private final ContactMechRepositoryJdbc repository;
    private final JdbcAggregateTemplate template;

    @Override
    public List<ContactMech> findAllForPerson(String personId) {
        return repository.findAllForPerson(personId).stream().map(dto -> {
            if (dto.isPostalAddress()) {
                return dto.toPostalAddress();
            } else if (dto.isTelecomNumber()) {
                return dto.toTelecomNumber();
            }

            return dto.toContactMech();
        }).toList();
    }
}
