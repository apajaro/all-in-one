package com.apajaro.platform.infrastructure.data.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table(name = "voucher_sequence")
public class VoucherSequenceDao {
    @Id
    private String id;

    @Column("organization_id")
    private String organizationId;

    @Column("voucher_type")
    private String voucherType;

    @Column("sequence_number")
    private Long sequenceNumber;
}
