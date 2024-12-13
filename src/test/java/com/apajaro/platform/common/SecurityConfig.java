package com.apajaro.platform.common;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

public class SecurityConfig {
    @Bean(name = "handlerExceptionResolver")
    public HandlerExceptionResolver createDefaultHandlerExceptionResolver() {
        return new DefaultHandlerExceptionResolver();
    }
}
