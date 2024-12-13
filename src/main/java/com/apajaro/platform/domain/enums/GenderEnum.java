package com.apajaro.platform.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenderEnum {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String value;
}
