package com.apajaro.platform.domain.valueobject;

import lombok.Value;

import java.util.Optional;

@Value
public class EmailAddress {
    String value;

    public EmailAddress(String value) {
        validate(value);
        this.value = value;
    }

    public static EmailAddress of(String emailAddress) {
        return Optional.ofNullable(emailAddress)
                .map(EmailAddress::new)
                .orElse(null);
    }

    private void validate(String value) {

    }
}
