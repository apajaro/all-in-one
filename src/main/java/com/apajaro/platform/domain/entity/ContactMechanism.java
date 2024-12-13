package com.apajaro.platform.domain.entity;

import com.apajaro.platform.domain.enums.ContactMechTypeEnum;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ContactMechanism extends ContactMech {
    ID id;
    String infoString;
    ContactMechTypeEnum contactMechType;
}
