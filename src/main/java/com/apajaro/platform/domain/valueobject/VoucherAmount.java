package com.apajaro.platform.domain.valueobject;

import com.apajaro.platform.domain.exception.DomainException;
import com.apajaro.platform.domain.exception.ErrorCode;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class VoucherAmount {
    BigDecimal value;

    public VoucherAmount(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    public static VoucherAmount of(BigDecimal amount) {
        return new VoucherAmount(amount);
    }

    private void validate(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException(ErrorCode.AMOUNT_CAN_NOT_BE_LESS_THAN_ZERO);
        }
    }
}
