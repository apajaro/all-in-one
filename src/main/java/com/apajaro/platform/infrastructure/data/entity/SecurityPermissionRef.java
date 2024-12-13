package com.apajaro.platform.infrastructure.data.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("security_group_permission")
public class SecurityPermissionRef {
    String securityPermissionId;
}
