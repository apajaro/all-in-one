package com.apajaro.platform.infrastructure.data.dto;

import com.apajaro.platform.domain.entity.ContactMechanism;
import com.apajaro.platform.domain.entity.PostalAddress;
import com.apajaro.platform.domain.entity.TelecomNumber;
import com.apajaro.platform.domain.entity.*;
import com.apajaro.platform.domain.enums.ContactMechPurposeEnum;
import com.apajaro.platform.domain.enums.ContactMechTypeEnum;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.Data;

@Data
public class ContactMechDTO {
    String id;
    String contactMechType;
    String contactMechPurpose;
    String infoString;
    String address1;
    String address2;
    String city;
    String state;
    String country;
    String postalCode;
    String countryCode;
    String areaCode;
    String contactNumber;

    public PostalAddress toPostalAddress() {
        return PostalAddress.builder()
                .id(ID.of(id))
                .address1(address1)
                .address2(address2)
                .city(city)
                .state(state)
                .country(country)
                .postalCode(postalCode)
                .contactMechPurpose(ContactMechPurposeEnum.valueOf(contactMechPurpose))
                .build();
    }

    public TelecomNumber toTelecomNumber() {
        return TelecomNumber.builder()
                .id(ID.of(id))
                .countryCode(countryCode)
                .areaCode(areaCode)
                .contactNumber(contactNumber)
                .contactMechPurpose(ContactMechPurposeEnum.valueOf(contactMechPurpose))
                .build();
    }

    public ContactMechanism toContactMech() {
        return ContactMechanism.builder()
                .id(ID.of(id))
                .infoString(infoString)
                .contactMechType(ContactMechTypeEnum.valueOf(contactMechType))
                .contactMechPurpose(ContactMechPurposeEnum.valueOf(contactMechPurpose))
                .build();
    }

    public boolean isPostalAddress() {
        return contactMechType.equals(ContactMechTypeEnum.POSTAL_ADDRESS.toString());
    }

    public boolean isTelecomNumber() {
        return contactMechType.equals(ContactMechTypeEnum.TELECOM_NUMBER.toString());
    }
}
