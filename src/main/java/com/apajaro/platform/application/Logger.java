package com.apajaro.platform.application;

public interface Logger {
    void info(String message);
    void error(String message);
    void error(String message, Exception exception);
}
