package com.apajaro.platform.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(1, "Internal Server Error"),
    VALIDATION_NOT_VALID(2, "Validation not valid"),
    UNAUTHORIZED(3, "Unauthorized"),
    INVALID_INPUT(4, "Invalid input"),

    INVALID_ID(10001, "Invalid ID"),

    NO_FILE_FOUND(10002, "No file found"),

    AMOUNT_CAN_NOT_BE_LESS_THAN_ZERO(20000, "Amount can not be less than zero"),
    VOUCHER_IS_INVALID(20001, "Voucher is invalid"),

    // Auth
    INVALID_CREDENTIALS(30001, "Invalid credentials"),
    USER_LOGIN_DISABLED_FOR_SECONDS(30002, "User is disabled for %s seconds"),
    USER_LOGIN_DISABLED(30003, "User is disabled"),
    USER_LOGIN_REQUIRES_PASSWORD_CHANGE(30004, "User requires password change"),
    USER_NOT_FOUND(30005, "User not found"),
    PERSON_NOT_FOUND(40001, "Person not found");

    private final Integer code;
    private final String message;
}
