package com.apajaro.platform.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ContactMechPurposeEnum {
    EMAIL_PRIMARY,
    PHONE_MOBILE,
    PHONE_HOME,
    POSTAL_HOME;
}
