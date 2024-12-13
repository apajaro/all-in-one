package com.apajaro.platform.infrastructure.web.controller.hr.dto;

import com.apajaro.platform.domain.entity.Person;
import com.apajaro.platform.domain.enums.EmploymentStatusEnum;
import com.apajaro.platform.domain.enums.GenderEnum;
import com.apajaro.platform.domain.enums.IdentificationTypeEnum;
import com.apajaro.platform.domain.enums.MaritalStatusEnum;
import com.apajaro.platform.domain.valueobject.ID;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
@Builder
public class PersonDto {
    String id;
    @NotEmpty
    String firstName;
    String middleName;
    @NotEmpty
    String lastName;
    String mothersMaidenName;
    @NotEmpty
    String gender;
    String birthDate;
    String identification;
    String identificationType;
    String maritalStatus;
    String employmentStatus;

    public static PersonDto from(Person person) {
        String birthDate = Optional.ofNullable(person.getBirthDate())
                .map(LocalDate::toString).orElse(null);
        String finalGender = Optional.ofNullable(person.getGender()).map(GenderEnum::toString)
                .orElse(null);
        String finalIdentificationType = Optional.ofNullable(person.getIdentificationType())
                .map(IdentificationTypeEnum::toString).orElse(null);
        String finalMaritalStatus = Optional.ofNullable(person.getMaritalStatus()).map(MaritalStatusEnum::toString)
                .orElse(null);
        String finalEmploymentStatus = Optional.ofNullable(person.getEmploymentStatus()).map(EmploymentStatusEnum::toString)
                .orElse(null);

        return PersonDto.builder()
                .id(person.getId().getValue())
                .firstName(person.getFirstName())
                .middleName(person.getMiddleName())
                .lastName(person.getLastName())
                .mothersMaidenName(person.getMothersMaidenName())
                .gender(finalGender)
                .birthDate(birthDate)
                .identification(person.getIdentification())
                .identificationType(finalIdentificationType)
                .maritalStatus(finalMaritalStatus)
                .employmentStatus(finalEmploymentStatus)
                .build();
    }

    public Person toDomain() {
        ID finalId = Optional.ofNullable(id).map(ID::of).orElse(null);
        IdentificationTypeEnum finalIdentificationType = Optional.ofNullable(identificationType)
                .map(IdentificationTypeEnum::valueOf).orElse(null);
        MaritalStatusEnum finalMaritalStatus = Optional.ofNullable(maritalStatus)
                .map(MaritalStatusEnum::valueOf).orElse(null);
        EmploymentStatusEnum finalEmploymentStatus = Optional.ofNullable(employmentStatus)
                .map(EmploymentStatusEnum::valueOf).orElse(null);
        LocalDate finalBirthDate = Optional.ofNullable(birthDate).map(LocalDate::parse).orElse(null);

        return Person.builder()
                .id(finalId)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .mothersMaidenName(mothersMaidenName)
                .gender(GenderEnum.valueOf(gender))
                .birthDate(finalBirthDate)
                .identification(identification)
                .identificationType(finalIdentificationType)
                .maritalStatus(finalMaritalStatus)
                .employmentStatus(finalEmploymentStatus)
                .build();
    }
}
