package com.apajaro.platform.infrastructure.web.controller.auth.dto;

import jakarta.validation.constraints.Email;

public record ForgotPasswordRequest(@Email String email) {
}
