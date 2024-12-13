package com.apajaro.platform.application.repository;

import com.apajaro.platform.domain.entity.Organization;

import java.util.Optional;

public interface OrganizationRepository {
    Optional<Organization> findById(String organizationId);

    Organization create(Organization organization);
    Organization update(Organization organization);
}
