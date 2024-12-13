package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.infrastructure.data.entity.OrganizationDao;
import org.springframework.data.repository.ListCrudRepository;

public interface OrganizationRepositoryJdbc extends ListCrudRepository<OrganizationDao, String> {
}
