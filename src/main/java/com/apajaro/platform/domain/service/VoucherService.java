package com.apajaro.platform.domain.service;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.domain.entity.Voucher;
import com.apajaro.platform.domain.enums.VoucherType;

/**
 * Port used to manage vouchers
 */
public interface VoucherService {
    /**
     * Returns all vouchers for a given organization.
     * It is possible to filter the results by using the search parameter.
     *
     * @param search
     * @param organizationId
     * @return
     */
    Page<Voucher> findAll(Search search, String organizationId);

    /**
     * Creates a new voucher for a given organization and persists it.
     *
     * @param voucher
     * @return
     */
    Voucher createVoucher(Voucher voucher);

    /**
     * Returns the next voucher number for a given voucher type and organization.
     * DEBIT and CREDIT vouchers have different number sequences, but also each organization has its own sequence tracking.
     *
     * @param voucherType
     * @param organizationId
     * @return
     */
    Long getNextVoucherNumber(VoucherType voucherType, String organizationId);
}
