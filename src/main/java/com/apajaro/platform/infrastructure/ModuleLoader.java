package com.apajaro.platform.infrastructure;

import com.apajaro.platform.application.BucketService;
import com.apajaro.platform.application.EmailService;
import com.apajaro.platform.application.Logger;
import com.apajaro.platform.application.PasswordEncoder;
import com.apajaro.platform.application.repository.*;
import com.apajaro.platform.application.service.*;
import com.apajaro.platform.domain.service.*;
import com.apajaro.platform.application.repository.*;
import com.apajaro.platform.application.service.*;
import com.apajaro.platform.domain.service.*;
import com.apajaro.platform.infrastructure.web.jwt.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ModuleLoader {

    @Bean
    public FileUploadService fileUploadService(FileMetadataRepository fileMetadataRepository, BucketService bucketService) {
        return new FileUploadServiceImpl(fileMetadataRepository, bucketService);
    }

    @Bean
    public JWTService jwtService(
            @Value("${app.security.jwtSecret}") String secret,
            @Value("${app.security.jwtExpirationInSeconds}") Integer expirationInSeconds,
            @Value("${app.security.jwtRefreshExpirationInDays}") Integer refreshExpirationInDays,
            Logger logger
    ) {
        return new JWTService(secret, expirationInSeconds, refreshExpirationInDays, logger);
    }

    @Bean
    public AuthService authService(
            UserLoginRepository userLoginRepository,
            UserLoginTokenRepository userLoginTokenRepository,
            UserLoginPasswordResetRepository userLoginPasswordResetRepository,
            SecurityPermissionRepository securityPermissionRepository,
            PersonRepository personRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService
    ) {
        return new AuthServiceImpl(
                userLoginRepository,
                userLoginTokenRepository,
                userLoginPasswordResetRepository,
                securityPermissionRepository,
                personRepository,
                passwordEncoder,
                emailService
        );
    }

    @Bean
    public VoucherService voucherService(
            VoucherRepository voucherRepository,
            VoucherSequenceRepository voucherSequenceRepository
    ) {
        return new VoucherServiceImpl(voucherRepository, voucherSequenceRepository);
    }

    @Bean
    public SecurityService securityService(
            SecurityPermissionRepository securityPermissionRepository,
            SecurityGroupRepository securityGroupRepository,
            UserLoginRepository userLoginRepository,
            PersonRepository personRepository,
            UserLoginPasswordResetRepository userLoginPasswordResetRepository,
            EmailService emailService,
            PasswordEncoder passwordEncoder
    ) {
        return new SecurityServiceImpl(
                securityPermissionRepository,
                securityGroupRepository,
                userLoginRepository,
                personRepository,
                userLoginPasswordResetRepository,
                emailService,
                passwordEncoder
        );
    }

    @Bean
    public HrService secretaryService(
            PersonRepository personRepository
    ) {
        return new HrServiceImpl(personRepository);
    }
}
