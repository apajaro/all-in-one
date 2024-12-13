package com.apajaro.platform.infrastructure.web.controller.hr;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.infrastructure.web.CurrentUser;
import com.apajaro.platform.infrastructure.web.UserPrincipal;
import com.apajaro.platform.infrastructure.web.controller.common.dto.FilterAndPaginationRequest;
import com.apajaro.platform.infrastructure.web.controller.hr.dto.PersonDto;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hr/people")
public interface PeopleResource {
    @GetMapping
    @PreAuthorize("hasAnyAuthority('people.write')")
    Page<PersonDto> getPeople(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String[] sort,
            @RequestParam(required = false) String direction,
            @CurrentUser UserPrincipal userPrincipal
    );

    @PostMapping("/lite")
    @PreAuthorize("hasAnyAuthority('people.write')")
    List<PersonDto> getPeopleLite(@Valid @RequestBody FilterAndPaginationRequest request, @CurrentUser UserPrincipal userPrincipal);

    @PostMapping
    @PreAuthorize("hasAnyAuthority('people.write')")
    PersonDto createPerson(@Valid @RequestBody PersonDto personDto, @CurrentUser UserPrincipal userPrincipal);

    @PutMapping(value = {"/{id}" })
    @PreAuthorize("hasAnyAuthority('people.write')")
    PersonDto updatePerson(
            @PathVariable String id,
            @Valid @RequestBody PersonDto personDto,
            @CurrentUser UserPrincipal userPrincipal
    );

    @DeleteMapping(value = {"/{id}" })
    @PreAuthorize("hasAnyAuthority('people.write')")
    void deletePerson(
            @PathVariable String id,
            @CurrentUser UserPrincipal userPrincipal
    );
}
