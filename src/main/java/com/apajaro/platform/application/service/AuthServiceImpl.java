package com.apajaro.platform.application.service;

import com.apajaro.platform.application.EmailService;
import com.apajaro.platform.application.PasswordEncoder;
import com.apajaro.platform.application.repository.*;
import com.apajaro.platform.domain.entity.*;
import com.apajaro.platform.application.repository.*;
import com.apajaro.platform.domain.entity.*;
import com.apajaro.platform.domain.service.AuthService;
import com.apajaro.platform.domain.exception.DomainException;
import com.apajaro.platform.domain.exception.ErrorCode;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.AllArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserLoginRepository userLoginRepository;
    private final UserLoginTokenRepository userLoginTokenRepository;
    private final UserLoginPasswordResetRepository userLoginPasswordResetRepository;
    private final SecurityPermissionRepository securityPermissionRepository;
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public UserLogin login(String username, String password) {
        UserLogin userLogin = userLoginRepository
                .findByUsername(username)
                .orElseThrow(() -> new DomainException(ErrorCode.INVALID_CREDENTIALS));
        Instant now = Instant.now();

        if (!userLogin.isEnabled()) {
            Instant disabledDateTime = userLogin.getDisabledDateTime();

            if (disabledDateTime != null) {
                if (disabledDateTime.isAfter(now)) {
                    long diffInSeconds = Duration.between(now, disabledDateTime).getSeconds();
                    if (diffInSeconds > 0) {
                        Map<String, Object> data = Map.of("seconds", diffInSeconds);
                        throw new DomainException(ErrorCode.USER_LOGIN_DISABLED_FOR_SECONDS, data);
                    }
                }
            } else {
                throw new DomainException(ErrorCode.USER_LOGIN_DISABLED);
            }
        }

        if (userLogin.isRequirePasswordChange()) {
            throw new DomainException(ErrorCode.USER_LOGIN_REQUIRES_PASSWORD_CHANGE);
        }

        if (!passwordEncoder.matches(password, userLogin.getPassword())) {
            userLogin.failedToLogin();

            userLoginRepository.update(userLogin, userLogin.getOrganizationId().getValue());
            throw new DomainException(ErrorCode.INVALID_CREDENTIALS);
        }

        userLogin.successfullyLoggedIn();
        userLoginRepository.update(userLogin, userLogin.getOrganizationId().getValue());

        personRepository.findById(userLogin.getPerson().getId().getValue(), userLogin.getOrganizationId().getValue())
                .ifPresent(userLogin::setPerson);
        return userLogin;
    }

    @Override
    public Set<SecurityPermission> getUserSecurityPermissions(ID userLoginId) {
        return securityPermissionRepository.getUserLoginPermissions(userLoginId.getValue());
    }

    @Override
    public Set<String> getUserPermissionSlugs(ID userLoginId) {
        Set<SecurityPermission> securityPermissions = getUserSecurityPermissions(userLoginId);

        return securityPermissions
                .stream()
                .map(SecurityPermission::getSlug)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<UserLogin> findUserLoginById(String id) {
        return userLoginRepository.findById(id);
    }

    @Override
    public UserLoginToken createUserLoginToken(String token, ID userLoginId) {
        UserLoginToken userLoginToken = UserLoginToken.builder()
                .id(ID.generate())
                .userLoginId(userLoginId)
                .token(token)
                .isExpired(false)
                .build();

        return userLoginTokenRepository.create(userLoginToken);
    }

    @Override
    public Boolean isUserLoginTokenExpired(String token, String userLoginId) {
        Optional<UserLoginToken> userLoginToken = userLoginTokenRepository.findByTokenAndUserLogin(token, userLoginId);

        return userLoginToken.isPresent() ? userLoginToken.get().getIsExpired() : true;
    }

    @Override
    public void expireUserLoginTokenToken(String token, String userLoginId) {
        userLoginTokenRepository.findByTokenAndUserLogin(token, userLoginId)
                .ifPresent((it) -> {
                    it.expire();
                    userLoginTokenRepository.update(it);
                });
    }

    @Override
    public void forgotPassword(String email) {
        Optional<UserLogin> optionalUserLogin = userLoginRepository.findByEmail(email);
        if (optionalUserLogin.isEmpty()) {
            return;
        }

        UserLogin userLogin = optionalUserLogin.get();
        Optional<Person> optionalPerson = personRepository.findById(userLogin.getPerson().getId().getValue(), userLogin.getOrganizationId().getValue());
        if (optionalUserLogin.isEmpty()) {
            return;
        }

        Person person = optionalPerson.get();

        Long validForSeconds = 300L;
        UserLoginPasswordReset userLoginPasswordReset = UserLoginPasswordReset.createForUser(userLogin.getId(), validForSeconds);
        userLoginPasswordResetRepository.create(userLoginPasswordReset);

        Map<String, Object> data = Map.of(
                "firstName", person.getFirstName(),
                "resetUrl", "http://localhost:5173/reset-password?token=" + userLoginPasswordReset.getToken().getValue(),
                "validForSeconds", validForSeconds,
                "signature", "AllinOne"
        );

        emailService.sendEmail(
                email,
                "Cambiar contraseña",
                "no-reply@AllinOne.com",
                "AllinOne",
                data, "resetPassword");
    }

    @Override
    public void resetPassword(String newPassword, String token) {
        Optional<UserLoginPasswordReset> optionalUserLoginPasswordReset = userLoginPasswordResetRepository.findByToken(token);
        if (optionalUserLoginPasswordReset.isEmpty()) {
            throw new DomainException(ErrorCode.UNAUTHORIZED);
        }

        UserLoginPasswordReset userLoginPasswordReset = optionalUserLoginPasswordReset.get();
        if (!userLoginPasswordReset.isValid()) {
            throw new DomainException(ErrorCode.UNAUTHORIZED);
        }

        Optional<UserLogin> optionalUserLogin = userLoginRepository.findById(userLoginPasswordReset.getUserLoginId().getValue());
        if (optionalUserLogin.isEmpty()) {
            throw new DomainException(ErrorCode.USER_NOT_FOUND);
        }

        UserLogin userLogin = optionalUserLogin.get();
        userLogin.setPassword(passwordEncoder.encode(newPassword));
        userLogin.enableUser();
        userLoginRepository.update(userLogin, userLogin.getOrganizationId().getValue());

        userLoginPasswordReset.setIsUsed(true);
        userLoginPasswordResetRepository.update(userLoginPasswordReset);
    }

    @Override
    public void verifyResetPasswordToken(String token) {
        UserLoginPasswordReset userLoginPasswordReset = userLoginPasswordResetRepository.findByToken(token)
                .orElseThrow(() -> new DomainException(ErrorCode.INVALID_INPUT));

        if (!userLoginPasswordReset.isValid()) {
            throw new DomainException(ErrorCode.INVALID_INPUT);
        }
    }
}
