package com.apajaro.platform.utils;

import com.apajaro.platform.domain.entity.*;
import com.github.javafaker.Faker;
import com.apajaro.platform.domain.entity.*;
import com.apajaro.platform.domain.valueobject.ID;

public class TestUtils {
    private static Faker faker = new Faker();

    public static Organization makeOrganization() {
        return makeOrganization(ID.generate());
    }

    public static Organization makeOrganization(ID id) {
        return Organization.builder()
                .id(id)
                .name(faker.name().name())
                .slogan(faker.lorem().sentence())
                .build();
    }

    public static SecurityPermission makeSecurityPermission(String slug) {
        return makeSecurityPermission(ID.generate(), slug);
    }
    public static SecurityPermission makeSecurityPermission(ID id, String slug) {
        return SecurityPermission.builder()
                .id(id)
                .name(faker.name().name())
                .description(faker.lorem().sentence())
                .slug(slug)
                .build();
    }

    public static SecurityGroup makeSecurityGroup() {
        return makeSecurityGroup(ID.generate());
    }

    public static SecurityGroup makeSecurityGroup(ID id) {
        return SecurityGroup.builder()
                .id(id)
                .name(faker.name().name())
                .description(faker.lorem().sentence())
                .build();
    }

    public static UserLogin makeUserLogin() {
        return makeUserLogin(ID.generate());
    }

    public static UserLogin makeUserLogin(ID id) {
        return UserLogin.builder()
                .id(id)
                .username(faker.name().username())
                .password(faker.internet().password())
                .enabled(true)
                .requirePasswordChange(false)
                .build();
    }

    public static Person makePerson() {
        return makePerson(ID.generate());
    }

    public static Person makePerson(ID id) {
        return Person.builder()
                .id(id)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();
    }

    /*public static SecurityPermission makeSecurityPermission() {
        return makeSecurityPermission(ID.generate());
    }
    public static SecurityPermission makeSecurityPermission(ID id) {
        return SecurityPermission.builder()
                .id(id)
                .name(faker.name().name())
                .description(faker.lorem().sentence())
                .slug(faker.lorem().word())
                .build();
    }

    public static Organization makeOrganization() {
        return makeOrganization(ID.generate());
    }

    public static Organization makeOrganization(ID id) {
        return Organization.builder()
                .id(id)
                .name(faker.company().name())
                .description(faker.lorem().sentence())
                .build();
    }

    public static SecurityGroup makeSecurityGroup() {
        return makeSecurityGroup(ID.generate());
    }

    public static SecurityGroup makeSecurityGroup(ID id) {
        return SecurityGroup.builder()
                .id(id)
                .name(faker.name().name())
                .description(faker.lorem().sentence())
                .build();
    }

    public static UserLogin makeUserLogin() {
        return makeUserLogin(ID.generate());
    }

    public static UserLogin makeUserLogin(ID id) {
        return UserLogin.builder()
                .id(id)
                .username(faker.name().username())
                .password(faker.internet().password())
                .enabled(true)
                .requirePasswordChange(false)
                .build();
    }*/
}
