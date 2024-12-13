package com.apajaro.platform.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MaritalStatusEnum {
    SINGLE,
    MARRIED,
    WIDOWED,
    DIVORCED,
    LIVING_COMMON_LAW;
}