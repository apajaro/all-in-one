package com.apajaro.platform.infrastructure.data.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("user_login_security_group")
public class SecurityGroupRef {
    String securityGroupId;
}
