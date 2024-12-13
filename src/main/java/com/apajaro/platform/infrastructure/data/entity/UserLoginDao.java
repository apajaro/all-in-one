package com.apajaro.platform.infrastructure.data.entity;

import com.apajaro.platform.domain.entity.Person;
import com.apajaro.platform.domain.entity.SecurityGroup;
import com.apajaro.platform.domain.entity.UserLogin;
import com.apajaro.platform.domain.valueobject.EmailAddress;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
@Table(name = "user_login")
public class UserLoginDao {
    @Id
    String id;

    @Column
    private String username;

    @Column
    private String password;

    @Column("password_hint")
    private String passwordHint;

    @Column
    private boolean enabled;

    @Column("require_password_change")
    private boolean requirePasswordChange;

    @Column("disabled_date_time")
    private Instant disabledDateTime;

    @Column("successive_failed_logins")
    private int successiveFailedLogins;

    @Column("last_currency_uom")
    private String lastCurrencyUom;

    @Column("email_address")
    private String emailAddress;

    @Column("last_locale")
    private String lastLocale;

    @Column("person_id")
    private String personId;

    @Column("organization_id")
    private String organizationId;

    @Builder.Default
    @MappedCollection(idColumn = "user_login_id")
    private Set<SecurityGroupRef> securityGroups = new HashSet<>();

    @Builder.Default
    @CreatedDate
    @Column("created_at")
    private Instant createdAt = Instant.now();

    @Builder.Default
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt = Instant.now();

    public void addSecurityGroup(String securityGroupId) {
        securityGroups.add(new SecurityGroupRef(securityGroupId));
    }

    public void addSecurityGroup(SecurityGroup securityGroup) {
        securityGroups.add(new SecurityGroupRef(securityGroup.getId().getValue()));
    }

    public UserLogin toDomain() {
        Person person = Person.builder()
                .id(ID.of(this.personId))
                .build();
        EmailAddress emailAddress = EmailAddress.of(this.emailAddress);

        UserLogin userLogin = UserLogin.builder()
                .id(ID.of(this.id))
                .username(this.username)
                .password(this.password)
                .passwordHint(this.passwordHint)
                .enabled(this.enabled)
                .requirePasswordChange(this.requirePasswordChange)
                .disabledDateTime(this.disabledDateTime)
                .successiveFailedLogins(this.successiveFailedLogins)
                .lastCurrencyUom(this.lastCurrencyUom)
                .emailAddress(emailAddress)
                .lastLocale(this.lastLocale)
                .person(person)
                .organizationId(ID.of(this.organizationId))
                .build();

        this.securityGroups
                .forEach(securityGroupRef -> userLogin.addSecurityGroup(SecurityGroup.builder().id(ID.of(securityGroupRef.securityGroupId)).build()));

        return userLogin;
    }

    public static UserLoginDao fromDomain(UserLogin userLogin) {
        String emailAddress = Optional.ofNullable(userLogin.getEmailAddress())
                .map(EmailAddress::getValue)
                .orElse(null);

        UserLoginDao userLoginDao = UserLoginDao
                .builder()
                .id(userLogin.getId().getValue())
                .username(userLogin.getUsername())
                .password(userLogin.getPassword())
                .passwordHint(userLogin.getPasswordHint())
                .enabled(userLogin.isEnabled())
                .requirePasswordChange(userLogin.isRequirePasswordChange())
                .disabledDateTime(userLogin.getDisabledDateTime())
                .successiveFailedLogins(userLogin.getSuccessiveFailedLogins())
                .lastCurrencyUom(userLogin.getLastCurrencyUom())
                .emailAddress(emailAddress)
                .lastLocale(userLogin.getLastLocale())
                .personId(userLogin.getPerson().getId().getValue())
                .organizationId(userLogin.getOrganizationId().getValue())
                .build();

        userLogin.getSecurityGroups().forEach(userLoginDao::addSecurityGroup);
        return userLoginDao;
    }
}
