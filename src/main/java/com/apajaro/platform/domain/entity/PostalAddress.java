package com.apajaro.platform.domain.entity;

import com.apajaro.platform.domain.valueobject.ID;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class PostalAddress extends ContactMech {
    ID id;
    String address1;
    String address2;
    String city;
    String state;
    String country;
    String postalCode;
}
