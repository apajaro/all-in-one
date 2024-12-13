package com.apajaro.platform.domain.entity;

import com.apajaro.platform.domain.enums.VoucherType;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
public class Voucher {
    private ID id;
    private VoucherType voucherType;
    private Long voucherNumber;
    private String city;
    private LocalDate voucherDate;
    private String counterParty;
    private String deliveredBySignatureUrl;
    private String receivedBySignatureUrl;
    private ID organizationId;
    @Builder.Default
    private Set<VoucherDetail> details = new HashSet<>();

    public void addDetail(VoucherDetail detail) {
        this.details.add(detail);
    }

    public boolean isValid() {
        return details != null && !details.isEmpty() && deliveredBySignatureUrl != null && receivedBySignatureUrl != null;
    }
}
