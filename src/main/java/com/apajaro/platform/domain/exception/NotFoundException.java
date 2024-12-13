package com.apajaro.platform.domain.exception;

import java.util.Map;

public class NotFoundException extends DomainException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundException(ErrorCode errorCode, Map<String, Object> data) {
        super(errorCode, data);
    }
}
