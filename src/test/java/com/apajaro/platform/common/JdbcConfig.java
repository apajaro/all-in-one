package com.apajaro.platform.common;

import com.apajaro.platform.application.repository.*;
import com.apajaro.platform.infrastructure.data.repository.*;
import com.apajaro.platform.application.repository.*;
import com.apajaro.platform.infrastructure.data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

@EnableJdbcRepositories(basePackages = "com.apajaro.platform")
public class JdbcConfig {
    @Bean
    public OrganizationRepository organizationRepository(
            @Autowired OrganizationRepositoryJdbc organizationRepositoryJdbc,
            @Autowired JdbcAggregateTemplate template
    ) {
        return new OrganizationRepositoryImpl(organizationRepositoryJdbc, template);
    }

    @Bean
    public SecurityPermissionRepository securityPermissionRepository(
            @Autowired SecurityPermissionRepositoryJdbc securityPermissionRepositoryJdbc,
            @Autowired JdbcAggregateTemplate template
    ) {
        return new SecurityPermissionRepositoryImpl(securityPermissionRepositoryJdbc, template);
    }

    @Bean
    public SecurityGroupRepository securityGroupRepository(
            @Autowired SecurityGroupRepositoryJdbc securityGroupRepositoryJdbc,
            @Autowired JdbcAggregateTemplate template
    ) {
        return new SecurityGroupRepositoryImpl(securityGroupRepositoryJdbc, template);
    }

    @Bean
    public PersonRepository personRepository(
            @Autowired PersonRepositoryJdbc personRepositoryJdbc,
            @Autowired JdbcAggregateTemplate jdbcAggregateTemplate,
            @Autowired JdbcTemplate jdbcTemplate
    ) {
        return new PersonRepositoryImpl(personRepositoryJdbc, jdbcAggregateTemplate, jdbcTemplate);
    }

    @Bean
    public UserLoginRepository userLoginRepository(
            @Autowired UserLoginRepositoryJdbc userLoginRepositoryJdbc,
            @Autowired PersonRepositoryJdbc personRepositoryJdbc,
            @Autowired JdbcAggregateTemplate template,
            @Autowired JdbcTemplate jdbcTemplate
    ) {
        return new UserLoginRepositoryImpl(
                userLoginRepositoryJdbc,
                personRepositoryJdbc,
                template,
                jdbcTemplate
        );
    }
    @Bean
    public VoucherRepository voucherRepository(
            @Autowired VoucherRepositoryJdbc voucherRepositoryJdbc,
            @Autowired JdbcAggregateTemplate template
    ) {
        return new VoucherRepositoryImpl(voucherRepositoryJdbc, template);
    }
    @Bean
    public VoucherSequenceRepository voucherSequenceRepository(
            @Autowired VoucherSequenceRepositoryJdbc voucherSequenceRepositoryJdbc,
            @Autowired JdbcAggregateTemplate template
    ) {
        return new VoucherSequenceRepositoryImpl(voucherSequenceRepositoryJdbc, template);
    }
}
