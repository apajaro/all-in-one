package com.apajaro.platform.infrastructure.exceptions;

import com.apajaro.platform.application.Logger;
import com.apajaro.platform.domain.exception.DomainException;
import com.apajaro.platform.domain.exception.ErrorCode;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestControllerAdvice
public class ExceptionHandlerAdvice {
    private final static String ERROR_CODE_FIELD_NAME = "errorCode";
    Logger logger;

    @ExceptionHandler(DomainException.class)
    public ProblemDetail handleDomainException(DomainException e) {
        logger.error("DomainException", e);
        ErrorCode errorCode = e.getErrorCode();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorCode.getMessage());
        problemDetail.setProperty(ERROR_CODE_FIELD_NAME, errorCode.getCode());
        problemDetail.setProperty("data", e.getData());
        return problemDetail;
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ProblemDetail handleAuthenticationException(AuthenticationException e) {
        logger.error("AuthenticationException", e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ProblemDetail handleAccessDeniedException(AccessDeniedException e) {
        logger.error("AccessDeniedException", e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(value = {JwtException.class})
    public ProblemDetail handleJwtException(JwtException e) {
        logger.error("JwtException", e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("MethodArgumentNotValidException", e);

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage);
        });

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, e.getMessage());

        problemDetail.setProperty(ERROR_CODE_FIELD_NAME, ErrorCode.INVALID_INPUT.getCode());
        problemDetail.setProperty("fields", errors);
        return problemDetail;
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ProblemDetail handleException(HttpMessageNotReadableException _e) {
        logger.error("Internal exception", _e);

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, errorCode.getMessage());
        problemDetail.setProperty(ERROR_CODE_FIELD_NAME, errorCode.getCode());

        return problemDetail;
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ProblemDetail handleException(Exception _e) {
        logger.error("Internal exception", _e);

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, errorCode.getMessage());
        problemDetail.setProperty(ERROR_CODE_FIELD_NAME, errorCode.getCode());

        return problemDetail;
    }
}
