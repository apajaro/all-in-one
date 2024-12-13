package com.apajaro.platform.domain.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class DomainException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Map<String, Object> data;

    public DomainException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public DomainException(ErrorCode errorCode, Map<String, Object> data) {
        super(errorCode.getMessage());

        this.errorCode = errorCode;
        this.data = data;
    }
}