package com.apajaro.platform.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ContactMechTypeEnum {
    EMAIL_ADDRESS,
    TELECOM_NUMBER,
    POSTAL_ADDRESS;
}
