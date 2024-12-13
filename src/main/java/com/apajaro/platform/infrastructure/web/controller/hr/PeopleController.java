package com.apajaro.platform.infrastructure.web.controller.hr;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.domain.entity.Person;
import com.apajaro.platform.domain.service.HrService;
import com.apajaro.platform.infrastructure.web.UserPrincipal;
import com.apajaro.platform.infrastructure.web.controller.common.dto.FilterAndPaginationRequest;
import com.apajaro.platform.infrastructure.web.controller.hr.dto.PersonDto;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PeopleController implements PeopleResource {
    private HrService hrService;

    @Override
    public Page<PersonDto> getPeople(String query, Integer page, Integer size, String[] sort, String direction, UserPrincipal userPrincipal) {
        Search search = Search.of(query, page, size, sort, direction);
        Page<Person> people = hrService.findAllPeople(search, userPrincipal.getOrganizationId());

        return Page.<PersonDto>builder()
                .content(people.getContent().stream().map(PersonDto::from).toList())
                .totalPages(people.getTotalPages())
                .totalElements(people.getTotalElements())
                .build();
    }

    @Override
    public List<PersonDto> getPeopleLite(FilterAndPaginationRequest request, UserPrincipal userPrincipal) {
        Search search = Search.of(request.getSearchTerm(), request.getPage(), request.getSize(), request.getSort());
        return hrService.findAllPeopleLite(search, userPrincipal.getOrganizationId()).stream()
                .map(PersonDto::from)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonDto createPerson(PersonDto personDto, UserPrincipal userPrincipal) {
        Person person = hrService.createPerson(personDto.toDomain(), userPrincipal.getOrganizationId());

        return PersonDto.from(person);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonDto updatePerson(String id, PersonDto personDto, UserPrincipal userPrincipal) {
        personDto.setId(id);
        Person person = hrService.updatePerson(personDto.toDomain(), userPrincipal.getOrganizationId());

        return PersonDto.from(person);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePerson(String id, UserPrincipal userPrincipal) {
        hrService.deletePerson(id, userPrincipal.getOrganizationId());
    }
}
