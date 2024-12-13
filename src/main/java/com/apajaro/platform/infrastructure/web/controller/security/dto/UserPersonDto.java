package com.apajaro.platform.infrastructure.web.controller.security.dto;

import com.apajaro.platform.domain.entity.Person;
import com.apajaro.platform.domain.enums.IdentificationTypeEnum;
import com.apajaro.platform.domain.valueobject.EmailAddress;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class UserPersonDto {
    String id;
    String firstName;
    String lastName;
    String identification;
    String identificationType;
    String emailAddress;

    public static UserPersonDto from(Person person) {
        String emailAddress = Optional
                .ofNullable(person.getEmailAddress())
                .map(EmailAddress::getValue)
                .orElse(null);
        String idType = Optional
                .ofNullable(person.getIdentificationType())
                .map(IdentificationTypeEnum::toString)
                .orElse(null);

        return UserPersonDto.builder()
                .id(person.getId().getValue())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .identification(person.getIdentification())
                .identificationType(idType)
                .emailAddress(emailAddress)
                .build();
    }
}
