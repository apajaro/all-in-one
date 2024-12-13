package com.apajaro.platform.infrastructure.web.controller.auth;

import com.apajaro.platform.domain.service.AuthService;
import com.apajaro.platform.domain.entity.UserLogin;
import com.apajaro.platform.domain.exception.ErrorCode;
import com.apajaro.platform.domain.exception.UnauthorizedException;
import com.apajaro.platform.infrastructure.utils.RequestUtils;
import com.apajaro.platform.infrastructure.web.controller.ApiResponse;
import com.apajaro.platform.infrastructure.web.controller.auth.dto.*;
import com.apajaro.platform.infrastructure.web.controller.auth.dto.*;
import com.apajaro.platform.infrastructure.web.jwt.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
public class AuthController implements AuthResource {
    private final AuthService authService;
    private JWTService jwtService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResponse login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        UserLogin userLogin = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        Set<String> permissionSlugs = authService.getUserPermissionSlugs(userLogin.getId());

        String accessToken = jwtService.generateAccessToken(userLogin, permissionSlugs);
        String refreshToken = jwtService.generateRefreshToken(userLogin.getId());

        authService.createUserLoginToken(refreshToken, userLogin.getId());

        Cookie refreshCookie = new Cookie(RequestUtils.REFRESH_TOKEN_COOKIE_NAME, refreshToken);
        refreshCookie.setMaxAge(60 * 60 * 24 * 60);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");

        response.addCookie(refreshCookie);
        return new LoginResponse(accessToken);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie refreshTokenCookie = RequestUtils.getRefreshTokenCookie(request)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.UNAUTHORIZED));

        String refreshToken = refreshTokenCookie.getValue();
        String userLoginId = jwtService.getUserIdFromToken(refreshToken);

        if (StringUtils.hasText(refreshToken) && jwtService.validateToken(refreshToken, true)) {
            Boolean isRefreshTokenExpired = authService.isUserLoginTokenExpired(refreshToken, userLoginId);
            if (isRefreshTokenExpired) {
                throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
            }

            UserLogin userLogin = authService.findUserLoginById(userLoginId)
                    .orElseThrow(() -> new UnauthorizedException(ErrorCode.USER_NOT_FOUND));
            Set<String> permissionSlugs = authService.getUserPermissionSlugs(userLogin.getId());
            String accessToken = jwtService.generateAccessToken(userLogin, permissionSlugs);

            return new LoginResponse(accessToken);
        }

        throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LogoutResponse logOut(HttpServletRequest request, HttpServletResponse response) {
        Cookie refreshTokenCookie = RequestUtils.getRefreshTokenCookie(request)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.UNAUTHORIZED));

        String refreshToken = refreshTokenCookie.getValue();
        String userLoginId = jwtService.getUserIdFromToken(refreshToken);
        authService.expireUserLoginTokenToken(refreshToken, userLoginId);

        Cookie cookie = new Cookie(RequestUtils.REFRESH_TOKEN_COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        return new LogoutResponse();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<String> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        authService.forgotPassword(forgotPasswordRequest.email());
        return new ApiResponse<>(true, "");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<String> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest.newPassword(), resetPasswordRequest.token());

        return new ApiResponse<>(true, "");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<String> resetPassword(String token) {
        authService.verifyResetPasswordToken(token);
        return new ApiResponse<>(true, "");
    }
}
