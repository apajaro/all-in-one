package com.apajaro.platform.infrastructure.web.controller.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ResetPasswordRequest(@NotNull @NotEmpty String newPassword, @NotNull @NotEmpty String token) {
}
