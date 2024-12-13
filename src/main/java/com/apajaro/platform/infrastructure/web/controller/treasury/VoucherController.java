package com.apajaro.platform.infrastructure.web.controller.treasury;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.domain.entity.Voucher;
import com.apajaro.platform.domain.enums.VoucherType;
import com.apajaro.platform.domain.service.VoucherService;
import com.apajaro.platform.domain.valueobject.ID;
import com.apajaro.platform.infrastructure.web.UserPrincipal;
import com.apajaro.platform.infrastructure.web.controller.treasury.dto.VoucherDto;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class VoucherController implements VoucherResource {
    private final VoucherService voucherService;

    @Override
    public Page<VoucherDto> getVouchers(
            String query,
            Integer page,
            Integer size,
            String[] sort,
            String direction,
            UserPrincipal userPrincipal) {

        Search search = Search.of(query, page, size, sort, direction);
        Page<Voucher> vouchers = voucherService.findAll(search, userPrincipal.getOrganizationId());

        return Page.<VoucherDto>builder()
                .content(vouchers.getContent().stream().map(VoucherDto::from).toList())
                .totalPages(vouchers.getTotalPages())
                .totalElements(vouchers.getTotalElements())
                .build();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VoucherDto createVoucher(VoucherDto voucherDto, UserPrincipal userPrincipal) {
        Voucher voucher = voucherDto.toDomain();
        voucher.setOrganizationId(ID.of(userPrincipal.getOrganizationId()));

        Voucher createdVoucher = voucherService.createVoucher(voucher);
        return VoucherDto.from(createdVoucher);
    }

    @Override
    public Long getNextVoucherNumber(String voucherType, UserPrincipal userPrincipal) {
        return voucherService.getNextVoucherNumber(VoucherType.valueOf(voucherType), userPrincipal.getOrganizationId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteVoucher(Long id, UserPrincipal userPrincipal) {

    }
}
