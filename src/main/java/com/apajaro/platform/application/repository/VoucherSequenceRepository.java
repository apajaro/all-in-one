package com.apajaro.platform.application.repository;

import com.apajaro.platform.domain.enums.VoucherType;

public interface VoucherSequenceRepository {
    void startSequence(VoucherType voucherType, String organizationId);

    Long getNextVoucherNumber(VoucherType voucherType, String organizationId);

    void updateNextVoucherNumber(VoucherType voucherType, String organizationId, Long nextVoucherNumber);
}
