package com.apajaro.platform.infrastructure.web.controller.auth;

import com.apajaro.platform.infrastructure.web.controller.ApiResponse;
import com.apajaro.platform.infrastructure.web.controller.auth.dto.*;
import com.apajaro.platform.infrastructure.web.controller.auth.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public interface AuthResource {

    @PostMapping(value = {"/login" })
    LoginResponse login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response);


    @PostMapping(value = {"/refresh-token" })
    LoginResponse refreshToken(HttpServletRequest request, HttpServletResponse response);

    @PostMapping(value = "/logout")
    LogoutResponse logOut(HttpServletRequest request, HttpServletResponse response);

    @PostMapping(value = "/forgot-password")
    ApiResponse<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest);

    @PostMapping(value = "/reset-password")
    ApiResponse<String> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest);

    @GetMapping(value = "/is-valid-reset-password-token")
    ApiResponse<String> resetPassword(@RequestParam String token);
}
