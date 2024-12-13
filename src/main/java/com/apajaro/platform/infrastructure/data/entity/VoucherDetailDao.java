package com.apajaro.platform.infrastructure.data.entity;

import com.apajaro.platform.domain.entity.VoucherDetail;
import com.apajaro.platform.domain.valueobject.ID;
import com.apajaro.platform.domain.valueobject.VoucherAmount;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
@Table(name = "voucher_detail")
public class VoucherDetailDao {
    @Id
    private String id;

    @Column
    private String description;

    @Column
    private BigDecimal amount;

    @Builder.Default
    private Set<String> contentUrls = new HashSet<>();

    @Column("voucher_id")
    private String voucherId;

    public VoucherDetail toDomain() {
        VoucherDetail voucherDetail = VoucherDetail
                .builder()
                .description(this.description)
                .amount(VoucherAmount.of(this.amount))
                .build();

        Optional.ofNullable(this.contentUrls).ifPresent(voucherDetail::setContentUrls);
        return voucherDetail;
    }

    public static VoucherDetailDao fromDomain(VoucherDetail entity) {
        return VoucherDetailDao
                .builder()
                .id(ID.generate().getValue())
                .description(entity.getDescription())
                .amount(entity.getAmount().getValue())
                .contentUrls(entity.getContentUrls())
                .build();
    }
}
