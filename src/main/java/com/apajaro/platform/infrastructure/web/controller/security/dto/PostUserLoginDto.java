package com.apajaro.platform.infrastructure.web.controller.security.dto;

import com.apajaro.platform.domain.entity.Person;
import com.apajaro.platform.domain.entity.SecurityGroup;
import com.apajaro.platform.domain.entity.UserLogin;
import com.apajaro.platform.domain.enums.IdentificationTypeEnum;
import com.apajaro.platform.domain.valueobject.EmailAddress;
import com.apajaro.platform.domain.valueobject.ID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
public class PostUserLoginDto {
    @NotNull
    @NotBlank
    String personId;
    @NotEmpty
    List<String> securityGroups;
    String identification;
    String identificationType;
    String emailAddress;

    public UserLogin toDomain() {
        IdentificationTypeEnum identificationType = Optional
                .ofNullable(this.identificationType)
                .map(IdentificationTypeEnum::valueOf)
                .orElse(null);
        EmailAddress emailAddress = Optional
                .ofNullable(this.emailAddress)
                .map(EmailAddress::of)
                .orElse(null);

        Person person = Person.builder()
                .id(ID.of(personId))
                .identificationType(identificationType)
                .identification(identification)
                .emailAddress(emailAddress)
                .build();

        Set<SecurityGroup> securityGroups = this.securityGroups
                .stream()
                .map(id -> SecurityGroup.builder().id(ID.of(id)).build())
                .collect(Collectors.toSet());

        return UserLogin.builder()
                .person(person)
                .securityGroups(securityGroups)
                .build();
    }
}
