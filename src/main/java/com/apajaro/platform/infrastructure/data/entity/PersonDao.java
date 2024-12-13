package com.apajaro.platform.infrastructure.data.entity;

import com.apajaro.platform.domain.entity.Person;
import com.apajaro.platform.domain.enums.EmploymentStatusEnum;
import com.apajaro.platform.domain.enums.GenderEnum;
import com.apajaro.platform.domain.enums.IdentificationTypeEnum;
import com.apajaro.platform.domain.enums.MaritalStatusEnum;
import com.apajaro.platform.domain.valueobject.EmailAddress;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@Data
@Builder
@Table(name = "person")
public class PersonDao {
    @Id
    private String id;

    @Column("first_name")
    private String firstName;

    @Column("middle_name")
    String middleName;

    @Column("last_name")
    private String lastName;

    @Column("mothers_maiden_name")
    private String mothersMaidenName;

    @Column
    private String gender;

    @Column("birth_date")
    LocalDate birthDate;

    @Column
    String identification;

    @Column("identification_type")
    String identificationType;

    @Column("marital_status")
    String maritalStatus;

    @Column("employment_status")
    String employmentStatus;

    @Column("email_address")
    String emailAddress;

    @Column("deceased_date")
    LocalDate deceasedDate;

    @Column("organization_id")
    private String organizationId;

    @Builder.Default
    @CreatedDate
    @Column("created_at")
    private Instant createdAt = Instant.now();

    @Builder.Default
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt = Instant.now();

    public Person toDomain() {
        IdentificationTypeEnum finalIdentificationType = Optional.ofNullable(identificationType)
                .map(IdentificationTypeEnum::valueOf).orElse(null);
        MaritalStatusEnum finalMaritalStatus = Optional.ofNullable(maritalStatus)
                .map(MaritalStatusEnum::valueOf).orElse(null);
        EmploymentStatusEnum finalEmploymentStatus = Optional.ofNullable(employmentStatus)
                .map(EmploymentStatusEnum::valueOf).orElse(null);
        EmailAddress emailAddress = Optional.ofNullable(this.emailAddress).map(EmailAddress::of).orElse(null);

        return Person
                .builder()
                .id(ID.of(id))
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .mothersMaidenName(mothersMaidenName)
                .gender(GenderEnum.valueOf(gender))
                .birthDate(birthDate)
                .identification(identification)
                .identificationType(finalIdentificationType)
                .maritalStatus(finalMaritalStatus)
                .employmentStatus(finalEmploymentStatus)
                .emailAddress(emailAddress)
                .build();
    }

    public static PersonDao fromDomain(Person person, String organizationId) {
        String personGender = Optional.ofNullable(person.getGender()).map(GenderEnum::getValue).orElse(null);
        String finalIdentificationType = Optional.ofNullable(person.getIdentificationType()).map(IdentificationTypeEnum::toString).orElse(null);
        String finalMaritalStatus = Optional.ofNullable(person.getMaritalStatus()).map(MaritalStatusEnum::toString).orElse(null);
        String finalEmploymentStatus = Optional.ofNullable(person.getEmploymentStatus()).map(EmploymentStatusEnum::toString).orElse(null);
        String finalEmailAddress = Optional.ofNullable(person.getEmailAddress()).map(EmailAddress::getValue).orElse(null);

        return PersonDao
                .builder()
                .id(person.getId().getValue())
                .firstName(person.getFirstName())
                .middleName(person.getMiddleName())
                .lastName(person.getLastName())
                .mothersMaidenName(person.getMothersMaidenName())
                .gender(personGender)
                .birthDate(person.getBirthDate())
                .identification(person.getIdentification())
                .identificationType(finalIdentificationType)
                .maritalStatus(finalMaritalStatus)
                .employmentStatus(finalEmploymentStatus)
                .deceasedDate(person.getDeceasedDate())
                .emailAddress(finalEmailAddress)
                .organizationId(organizationId)
                .build();
    }
}