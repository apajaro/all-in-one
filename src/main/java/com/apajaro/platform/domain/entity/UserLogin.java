package com.apajaro.platform.domain.entity;

import com.apajaro.platform.domain.valueobject.EmailAddress;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class UserLogin {
    ID id;
    String username;
    String password;
    String passwordHint;
    boolean enabled;
    boolean requirePasswordChange;
    Instant disabledDateTime;
    int successiveFailedLogins;
    String lastCurrencyUom;
    EmailAddress emailAddress;

    @Builder.Default
    String lastLocale = "es";
    @Builder.Default
    Set<SecurityGroup> securityGroups = new HashSet<>();
    Person person;
    ID organizationId;

    public void failedToLogin() {
        int newSuccessiveFailedLogins = this.successiveFailedLogins + 1;
        boolean shouldBeDisabled = newSuccessiveFailedLogins >= 3;

        this.enabled = !shouldBeDisabled;
        this.disabledDateTime = shouldBeDisabled ? Instant.now().plusSeconds(300) : null;
        this.successiveFailedLogins = newSuccessiveFailedLogins;
    }

    public void successfullyLoggedIn() {
        this.enabled = true;
        this.disabledDateTime = null;
        this.successiveFailedLogins = 0;
    }

    public void addSecurityGroup(String securityGroupId) {
        securityGroups.add(SecurityGroup.builder().id(ID.of(securityGroupId)).build());
    }

    public void addSecurityGroup(SecurityGroup securityGroup) {
        securityGroups.add(securityGroup);
    }

    public void setDefaultValues() {
        setEnabled(true);
        setSuccessiveFailedLogins(0);
        setRequirePasswordChange(true);
    }

    public void enableUser() {
        setEnabled(true);
        setDisabledDateTime(null);
        setRequirePasswordChange(false);
    }
}
