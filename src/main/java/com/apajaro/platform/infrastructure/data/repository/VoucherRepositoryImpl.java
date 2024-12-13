package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.application.repository.VoucherRepository;
import com.apajaro.platform.domain.entity.Voucher;
import com.apajaro.platform.infrastructure.data.PaginationUtils;
import com.apajaro.platform.infrastructure.data.entity.VoucherDao;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class VoucherRepositoryImpl implements VoucherRepository {
    private final VoucherRepositoryJdbc voucherRepositoryJdbc;
    private final JdbcAggregateTemplate template;

    @Override
    public Page<Voucher> findAll(Search search, String organizationId) {
        Pageable pageable = PaginationUtils.toPageable(search.getPagination());
        var pagedResult = voucherRepositoryJdbc.findByOrganizationIdAndDeletedAtIsNull(organizationId, pageable);
        List<Voucher> vouchers = pagedResult.getContent().stream().map(VoucherDao::toDomain).toList();

        return Page.<Voucher>builder()
                .content(vouchers)
                .totalElements(pagedResult.getTotalElements())
                .totalPages(pagedResult.getTotalPages())
                .build();
    }

    @Override
    public Voucher createVoucher(Voucher voucher) {
        return template.insert(VoucherDao.fromDomain(voucher)).toDomain();
    }

    @Override
    public Voucher updateVoucher(Voucher voucher) {
        return null;
    }

    @Override
    public void deleteVoucher(Voucher voucher) {
        VoucherDao voucherDao = VoucherDao.fromDomain(voucher);
        voucherDao.delete();

        template.update(voucherDao);
    }
}
