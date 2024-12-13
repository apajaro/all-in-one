package com.apajaro.platform.application.repository;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.domain.entity.Voucher;

public interface VoucherRepository {
    Page<Voucher> findAll(Search search, String organizationId);

    Voucher createVoucher(Voucher voucher);
    Voucher updateVoucher(Voucher voucher);
    void deleteVoucher(Voucher voucher);
}
