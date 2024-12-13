package com.apajaro.platform.infrastructure.web.controller.treasury.dto;

import com.apajaro.platform.domain.entity.Voucher;
import com.apajaro.platform.domain.enums.VoucherType;
import com.apajaro.platform.domain.valueobject.ID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Value
public class VoucherDto {
    String id;
    @NotBlank
    String type;
    Long voucherNumber;
    String city;
    @NotNull
    String voucherDate;
    String counterParty;
    @NotBlank
    String deliveredBySignatureUrl;
    @NotBlank
    String receivedBySignatureUrl;
    @Builder.Default
    Set<VoucherDetailDto> details = new HashSet<>();

    public static VoucherDto from(Voucher voucher) {
        return VoucherDto.builder()
                .id(voucher.getId().getValue())
                .type(voucher.getVoucherType().name())
                .voucherNumber(voucher.getVoucherNumber())
                .city(voucher.getCity())
                .voucherDate(voucher.getVoucherDate().toString())
                .counterParty(voucher.getCounterParty())
                .deliveredBySignatureUrl(voucher.getDeliveredBySignatureUrl())
                .receivedBySignatureUrl(voucher.getReceivedBySignatureUrl())
                .details(voucher.getDetails().stream().map(VoucherDetailDto::from).collect(Collectors.toSet()))
                .build();
    }

    public Voucher toDomain() {
        Voucher voucher = Voucher.builder()
                .voucherType(VoucherType.valueOf(type))
                .voucherNumber(voucherNumber)
                .city(city)
                .voucherDate(LocalDate.parse(voucherDate))
                .counterParty(counterParty)
                .deliveredBySignatureUrl(deliveredBySignatureUrl)
                .receivedBySignatureUrl(receivedBySignatureUrl)
                .details(details.stream().map(VoucherDetailDto::toDomain).collect(Collectors.toSet()))
                .build();

        Optional.ofNullable(id).ifPresent(id -> voucher.setId(ID.of(id)));
        return voucher;
    }
}
