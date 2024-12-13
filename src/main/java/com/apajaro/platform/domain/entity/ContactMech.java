package com.apajaro.platform.domain.entity;

import com.apajaro.platform.domain.enums.ContactMechPurposeEnum;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class ContactMech {
    ContactMechPurposeEnum contactMechPurpose;
}
