package com.apajaro.platform.infrastructure.web.controller.treasury.dto;

import com.apajaro.platform.domain.entity.VoucherDetail;
import com.apajaro.platform.domain.valueobject.VoucherAmount;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Builder
@Value
public class VoucherDetailDto {
    @NotBlank
    String description;
    @NotBlank
    BigDecimal amount;
    @Builder.Default
    Set<String> contentUrls = new HashSet<>();

    public static VoucherDetailDto from(VoucherDetail voucherDetail) {
        return VoucherDetailDto.builder()
                .description(voucherDetail.getDescription())
                .amount(voucherDetail.getAmount().getValue())
                .contentUrls(voucherDetail.getContentUrls())
                .build();
    }

    public VoucherDetail toDomain() {
        return VoucherDetail.builder()
                .description(description)
                .amount(VoucherAmount.of(amount))
                .contentUrls(contentUrls)
                .build();
    }
}
