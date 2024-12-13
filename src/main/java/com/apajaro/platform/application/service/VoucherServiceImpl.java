package com.apajaro.platform.application.service;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.application.input.Search;
import com.apajaro.platform.application.repository.VoucherRepository;
import com.apajaro.platform.application.repository.VoucherSequenceRepository;
import com.apajaro.platform.domain.entity.Voucher;
import com.apajaro.platform.domain.enums.VoucherType;
import com.apajaro.platform.domain.exception.DomainException;
import com.apajaro.platform.domain.exception.ErrorCode;
import com.apajaro.platform.domain.service.VoucherService;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    VoucherRepository voucherRepository;
    VoucherSequenceRepository voucherSequenceRepository;

    @Override
    public Page<Voucher> findAll(Search search, String organizationId) {
        return voucherRepository.findAll(search, organizationId);
    }

    @Override
    public Voucher createVoucher(Voucher voucher) {
        if (!voucher.isValid()) {
            throw new DomainException(ErrorCode.VOUCHER_IS_INVALID);
        }

        String organizationId = voucher.getOrganizationId().getValue();
        // To prevent sequence collision, we need to get the next voucher number before creating the voucher
        Long nextVoucherId = this.getNextVoucherNumber(voucher.getVoucherType(), organizationId);

        voucher.setId(ID.generate());
        voucher.setVoucherNumber(nextVoucherId);
        Voucher newVoucher = voucherRepository.createVoucher(voucher);
        voucherSequenceRepository.updateNextVoucherNumber(voucher.getVoucherType(), organizationId, nextVoucherId);

        return newVoucher;
    }

    @Override
    public Long getNextVoucherNumber(VoucherType voucherType, String organizationId) {
        Long voucherSequence = voucherSequenceRepository.getNextVoucherNumber(voucherType, organizationId);
        if (voucherSequence == 0L) {
            voucherSequenceRepository.startSequence(voucherType, organizationId);
            return 1L;
        }

        return voucherSequence + 1L;
    }
}
