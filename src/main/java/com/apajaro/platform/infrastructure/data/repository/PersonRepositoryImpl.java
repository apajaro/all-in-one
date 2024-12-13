package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.application.repository.PersonRepository;
import com.apajaro.platform.domain.entity.Person;
import com.apajaro.platform.domain.valueobject.ID;
import com.apajaro.platform.infrastructure.data.PaginationUtils;
import com.apajaro.platform.infrastructure.data.entity.PersonDao;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {
    private final PersonRepositoryJdbc repository;
    private final JdbcAggregateTemplate template;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public Optional<Person> findById(String id, String organizationId) {
        return repository.findById(id).map(PersonDao::toDomain);
    }

    @Override
    public Person create(Person person, String organizationId) {
        return template.insert(PersonDao.fromDomain(person, organizationId)).toDomain();
    }

    @Override
    public Person update(Person person, String organizationId) {
        return template.update(PersonDao.fromDomain(person, organizationId)).toDomain();
    }

    @Override
    public Page<Person> findAll(Search search, String organizationId) {
        Pageable pageable = PaginationUtils.toPageable(search.getPagination());
        var pagedResult = repository.findByOrganizationId(organizationId, pageable);
        List<Person> people = pagedResult.getContent().stream().map(PersonDao::toDomain).toList();

        return Page.<Person>builder()
                .content(people)
                .totalElements(pagedResult.getTotalElements())
                .totalPages(pagedResult.getTotalPages())
                .build();
    }

    @Override
    public List<Person> findAllLite(Search search, String organizationId) {
        String query = "SELECT id, first_name, last_name FROM person WHERE organization_id = ?";
        if (search.getQuery() != null) {
            String likeText = "%" + search.getQuery().toUpperCase() + "%";
            query += " AND (UPPER(first_name) LIKE '%" + likeText + "%' OR UPPER(last_name) LIKE '%" + likeText + "%')";
        }

        return jdbcTemplate.query(query, (rs, rowNum) -> {
            return Person.builder()
                    .id(ID.of(rs.getString("id")))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .build();
        }, organizationId);
    }

    @Override
    public void delete(String id, String organizationId) {
        repository.deleteByIdAndOrganizationId(id, organizationId);
    }
}
