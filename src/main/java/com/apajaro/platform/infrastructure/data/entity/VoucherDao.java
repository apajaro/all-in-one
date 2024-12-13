package com.apajaro.platform.infrastructure.data.entity;

import com.apajaro.platform.domain.entity.Voucher;
import com.apajaro.platform.domain.enums.VoucherType;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Table(name = "voucher")
public class VoucherDao {
    @Id
    private String id;

    @Column("voucher_type")
    private String voucherType;

    @Column("voucher_number")
    private Long voucherNumber;

    @Column("city")
    private String city;

    @Column("voucher_date")
    private LocalDate voucherDate;

    @Column("counter_party")
    private String counterParty;

    @Column("delivered_by_signature_url")
    private String deliveredBySignatureUrl;

    @Column("received_by_signature_url")
    private String receivedBySignatureUrl;

    @Builder.Default
    @MappedCollection(idColumn = "voucher_id")
    private Set<VoucherDetailDao> details = new HashSet<>();

    @Column("organization_id")
    private String organizationId;

    @Builder.Default
    @CreatedDate
    @Column("created_at")
    private Instant createdAt = Instant.now();

    @Builder.Default
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt = Instant.now();

    @Column("deleted_at")
    private Instant deletedAt;

    private void addDetail(VoucherDetailDao detail) {
        this.details.add(detail);
    }

    public void delete() {
        this.deletedAt = Instant.now();
    }

    public Voucher toDomain() {
        Voucher voucher = Voucher
                .builder()
                .id(ID.of(this.id))
                .voucherType(VoucherType.valueOf(this.voucherType))
                .voucherNumber(this.voucherNumber)
                .city(this.city)
                .voucherDate(this.voucherDate)
                .counterParty(this.counterParty)
                .deliveredBySignatureUrl(this.deliveredBySignatureUrl)
                .receivedBySignatureUrl(this.receivedBySignatureUrl)
                .organizationId(ID.of(this.organizationId))
                .build();

        this.details.forEach(detail -> voucher.addDetail(detail.toDomain()));
        return voucher;
    }

    public static VoucherDao fromDomain(Voucher entity) {
        VoucherDao voucherDao = VoucherDao
                .builder()
                .id(entity.getId().getValue())
                .voucherType(entity.getVoucherType().name())
                .voucherNumber(entity.getVoucherNumber())
                .city(entity.getCity())
                .voucherDate(entity.getVoucherDate())
                .counterParty(entity.getCounterParty())
                .deliveredBySignatureUrl(entity.getDeliveredBySignatureUrl())
                .receivedBySignatureUrl(entity.getReceivedBySignatureUrl())
                .organizationId(entity.getOrganizationId().getValue())
                .build();

        entity.getDetails().forEach(detail -> voucherDao.addDetail(VoucherDetailDao.fromDomain(detail)));
        return voucherDao;
    }
}
