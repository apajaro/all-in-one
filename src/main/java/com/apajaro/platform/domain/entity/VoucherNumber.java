package com.apajaro.platform.domain.entity;

import com.apajaro.platform.domain.enums.VoucherType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VoucherNumber {
    private Long sequenceNumber;
    private VoucherType voucherType;

    /**
     * Get the next sequence number for the voucher type.
     * @return
     */
    public Long getSequenceNumber() {
        return sequenceNumber == null ? 0 : sequenceNumber + 1;
    }
}
