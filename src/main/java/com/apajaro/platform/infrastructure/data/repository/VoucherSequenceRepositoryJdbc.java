package com.apajaro.platform.infrastructure.data.repository;


import com.apajaro.platform.infrastructure.data.entity.VoucherSequenceDao;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface VoucherSequenceRepositoryJdbc extends ListCrudRepository<VoucherSequenceDao, String>, PagingAndSortingRepository<VoucherSequenceDao, String> {
    Optional<VoucherSequenceDao> findByVoucherTypeAndOrganizationId(String voucherType, String organizationId);
}
