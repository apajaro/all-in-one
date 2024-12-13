package com.apajaro.platform.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmploymentStatusEnum {
    FULL_TIME,
    PART_TIME,
    SELF_EMPLOYED,
    RETIRED,
    STUDENT,
    UNEMPLOYED,
    HOUSE;
}