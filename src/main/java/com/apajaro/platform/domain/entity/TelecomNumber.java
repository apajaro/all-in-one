package com.apajaro.platform.domain.entity;

import com.apajaro.platform.domain.valueobject.ID;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class TelecomNumber extends ContactMech {
    ID id;
    String countryCode;
    String areaCode;
    String contactNumber;
}
