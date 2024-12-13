package com.apajaro.platform.infrastructure.web.controller;

import lombok.Value;

@Value
public class ApiResponse<T> {
    Boolean success;
    T data;
}
