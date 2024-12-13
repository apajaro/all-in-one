package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.infrastructure.data.entity.VoucherDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VoucherRepositoryJdbc extends ListCrudRepository<VoucherDao, String>, PagingAndSortingRepository<VoucherDao, String> {
    Page<VoucherDao> findByOrganizationIdAndDeletedAtIsNull(String organizationId, Pageable pageable);
}
