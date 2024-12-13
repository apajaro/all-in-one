package com.apajaro.platform.domain.entity;

import com.apajaro.platform.domain.enums.EmploymentStatusEnum;
import com.apajaro.platform.domain.enums.GenderEnum;
import com.apajaro.platform.domain.enums.IdentificationTypeEnum;
import com.apajaro.platform.domain.enums.MaritalStatusEnum;
import com.apajaro.platform.domain.valueobject.EmailAddress;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Person {
    ID id;
    String firstName;
    String middleName;
    String lastName;
    String mothersMaidenName;
    GenderEnum gender;
    LocalDate birthDate;
    String identification;
    IdentificationTypeEnum identificationType;
    MaritalStatusEnum maritalStatus;
    EmploymentStatusEnum employmentStatus;
    LocalDate deceasedDate;
    EmailAddress emailAddress;

    public Boolean canCreateUserLogin() {
        return identificationType != null && identification != null && emailAddress != null;
    }

    public void setDataForUserLogin(
            IdentificationTypeEnum identificationType,
            String identification,
            EmailAddress emailAddress
    ) {
        if (this.identificationType == null) {
            this.identificationType = identificationType;
        }

        if (this.identification == null) {
            this.identification = identification;
        }

        if (this.emailAddress == null) {
            this.emailAddress = emailAddress;
        }
    }
}
