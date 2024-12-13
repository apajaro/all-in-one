package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.application.repository.UserLoginRepository;
import com.apajaro.platform.domain.entity.Person;
import com.apajaro.platform.domain.entity.SecurityGroup;
import com.apajaro.platform.domain.entity.UserLogin;
import com.apajaro.platform.domain.enums.IdentificationTypeEnum;
import com.apajaro.platform.domain.valueobject.EmailAddress;
import com.apajaro.platform.domain.valueobject.ID;
import com.apajaro.platform.infrastructure.data.PaginationUtils;
import com.apajaro.platform.infrastructure.data.dto.UserLoginPersonDTO;
import com.apajaro.platform.infrastructure.data.entity.UserLoginDao;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserLoginRepositoryImpl implements UserLoginRepository {
    private final UserLoginRepositoryJdbc repository;
    private final PersonRepositoryJdbc personRepository;
    private final JdbcAggregateTemplate template;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<UserLogin> findByUsername(String username) {
        return repository.findByUsername(username).map(UserLoginDao::toDomain);
    }

    @Override
    public UserLogin create(UserLogin userLogin, String organizationId) {
        return template.insert(UserLoginDao.fromDomain(userLogin)).toDomain();
    }

    @Override
    public UserLogin update(UserLogin userLogin, String organizationId) {
        return template.update(UserLoginDao.fromDomain(userLogin)).toDomain();
    }

    @Override
    public void delete(String userLoginId, String organizationId) {
        repository.deleteById(userLoginId);
    }

    @Override
    public Optional<UserLogin> findById(String id) {
        return repository.findById(id).map(UserLoginDao::toDomain);
    }

    @Override
    public Optional<UserLogin> findById(String id, String organizationId) {
        return repository.findByIdAndOrganizationIdCustom(id, organizationId).map(UserLoginPersonDTO::toUserLogin);
    }

    @Override
    public Optional<UserLogin> findByEmail(String email) {
        return repository.findByEmailAddress(email).map(UserLoginDao::toDomain);
    }

    @Override
    public Page<UserLogin> findAll(Search search, String organizationId) {
        Pageable pageable = PaginationUtils.toPageable(search.getPagination());
        List<UserLoginPersonDTO> pagedResult = repository.findByOrganizationIdCustom(organizationId, pageable);
        long totalElements = repository.countByOrganizationId(organizationId);

        List<UserLogin> userLogins = pagedResult.stream().map(UserLoginPersonDTO::toUserLogin).toList();

        var results = PageableExecutionUtils.getPage(userLogins, pageable, () -> totalElements);

        return Page.<UserLogin>builder()
                .content(results.getContent())
                .totalPages(results.getTotalPages())
                .totalElements(results.getTotalElements())
                .build();
    }

    @Override
    public List<Person> findPeople(String organizationId, Search search) {
        String query = "SELECT DISTINCT(p.id), p.first_name, p.last_name, p.identification_type, p.identification, p.email_address FROM person p  LEFT JOIN user_login ul on ul.person_id = p.id WHERE p.organization_id = ? AND ul.id IS NULL";
        if (search.getQuery() != null && !search.getQuery().isBlank()) {
            String likeText = "%" + search.getQuery().toUpperCase() + "%";
            query += " AND (UPPER(p.first_name) LIKE '%" + likeText + "%' OR UPPER(p.last_name) LIKE '%" + likeText + "%' OR UPPER(p.email_address) LIKE '%" + likeText + "%')";
        }

        query += " order by p.first_name, p.last_name";
        query += " limit 10";

        return jdbcTemplate.query(query, (rs, rowNum) -> {
            EmailAddress emailAddress = Optional.ofNullable(rs.getString("email_address"))
                    .map(EmailAddress::of)
                    .orElse(null);
            IdentificationTypeEnum identificationType = Optional
                    .ofNullable(rs.getString("identification_type"))
                    .map(IdentificationTypeEnum::valueOf)
                    .orElse(null);

            return Person.builder()
                    .id(ID.of(rs.getString("id")))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .identification(rs.getString("identification"))
                    .identificationType(identificationType)
                    .emailAddress(emailAddress)
                    .build();
        }, organizationId);
    }

    /**
     * Add security groups from JSON obtained from query result.
     *
     * @param userLogin
     * @param securityGroupsJSON
     */
    private void addSecurityGroupsFromJSON(UserLogin userLogin, String securityGroupsJSON) {
        try {
            JSONParser parser = new JSONParser(securityGroupsJSON);
            LinkedHashMap<String, Object> maps = parser.parseObject();
            List<LinkedHashMap> securityGroups = (List<LinkedHashMap>) maps.get("security_groups");

            securityGroups.forEach(securityGroup -> {
                String securityGroupId = securityGroup.get("id").toString();
                String securityGroupName = securityGroup.get("name").toString();

                userLogin.addSecurityGroup(SecurityGroup.of(securityGroupId, securityGroupName));
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
