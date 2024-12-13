package com.apajaro.platform.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TenantContentType {
    LOGO("LOGO");

    private final String value;
}
