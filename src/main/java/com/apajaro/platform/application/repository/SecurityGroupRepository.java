package com.apajaro.platform.application.repository;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.domain.entity.SecurityGroup;

import java.util.Optional;

public interface SecurityGroupRepository {
    Page<SecurityGroup> findAll(String organizationId, Search search);
    Optional<SecurityGroup> findById(String id, String organizationId);

    SecurityGroup create(SecurityGroup securityGroup, String organizationId);
    SecurityGroup update(SecurityGroup securityGroup, String organizationId);
    void delete(String id, String organizationId);
}
