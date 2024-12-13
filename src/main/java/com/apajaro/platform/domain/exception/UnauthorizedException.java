package com.apajaro.platform.domain.exception;

import java.util.Map;

public class UnauthorizedException extends DomainException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UnauthorizedException(ErrorCode errorCode, Map<String, Object> data) {
        super(errorCode, data);
    }
}
