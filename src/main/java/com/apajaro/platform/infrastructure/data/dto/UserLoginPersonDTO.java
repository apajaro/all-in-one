package com.apajaro.platform.infrastructure.data.dto;

import com.apajaro.platform.domain.entity.Person;
import com.apajaro.platform.domain.entity.SecurityGroup;
import com.apajaro.platform.domain.entity.UserLogin;
import com.apajaro.platform.domain.valueobject.EmailAddress;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.Data;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Data
public class UserLoginPersonDTO {
    String id;
    String username;
    String password;
    String passwordHint;
    boolean enabled;
    boolean requirePasswordChange;
    Instant disabledDateTime;
    int successiveFailedLogins;
    String lastCurrencyUom;
    EmailAddress emailAddress;
    String personId;
    String firstName;
    String lastName;
    Object securityGroups;

    public UserLogin toUserLogin() {
        Person person = Person.builder().id(ID.of(this.personId))
                .firstName(this.firstName)
                .lastName(this.lastName)
                .build();

        UserLogin userLogin = UserLogin.builder()
                .id(ID.of(id))
                .username(username)
                .password(password)
                .passwordHint(passwordHint)
                .enabled(enabled)
                .requirePasswordChange(requirePasswordChange)
                .disabledDateTime(disabledDateTime)
                .successiveFailedLogins(successiveFailedLogins)
                .lastCurrencyUom(lastCurrencyUom)
                .emailAddress(emailAddress)
                .person(person)
                .build();

        try {
            if (securityGroups == null) {
                return userLogin;
            }

            JSONParser parser = new JSONParser(securityGroups.toString());
            ArrayList<LinkedHashMap<String, String>> securityGroups =
                    (ArrayList<LinkedHashMap<String, String>>) parser.parseObject().get("security_groups");
            securityGroups.forEach(securityGroup -> {
                String securityGroupId = securityGroup.get("id");
                String securityGroupName = securityGroup.get("name");

                if (securityGroupId == null || securityGroupName == null) {
                    return;
                }

                userLogin.addSecurityGroup(SecurityGroup.of(securityGroupId, securityGroupName));
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return userLogin;
    }
}
