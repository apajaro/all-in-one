package com.apajaro.platform.infrastructure.web.controller.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchUserLoginDto {
    List<String> securityGroups;
}
