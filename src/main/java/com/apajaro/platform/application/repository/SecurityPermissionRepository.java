package com.apajaro.platform.application.repository;

import com.apajaro.platform.domain.entity.SecurityPermission;

import java.util.Set;

public interface SecurityPermissionRepository {

    Set<SecurityPermission> findAll();
    Set<SecurityPermission> getUserLoginPermissions(String userLoginId);
}
