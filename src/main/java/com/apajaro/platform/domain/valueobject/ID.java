package com.apajaro.platform.domain.valueobject;

import com.apajaro.platform.domain.exception.DomainException;
import com.apajaro.platform.domain.exception.ErrorCode;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class ID implements Serializable {
    String value;

    public ID(String value) {
        this.validate(value);
        this.value = value;
    }

    public static ID generate() {
        return ID.of(UUID.randomUUID().toString());
    }

    public static ID of(String id) {
        return new ID(id);
    }

    private void validate(String value) {
        try {
            UUID.fromString(value);
        } catch (RuntimeException e) {
            throw new DomainException(ErrorCode.INVALID_ID);
        }
    }
}