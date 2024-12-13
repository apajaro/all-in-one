package com.apajaro.platform.infrastructure.web.controller.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;


@Value
public class LoginRequest {
    @NotBlank(message = "username is required")
    @NotNull(message = "username is required")
    String username;

    @NotBlank(message = "password is required")
    @NotNull(message = "password is required")
    String password;
}
