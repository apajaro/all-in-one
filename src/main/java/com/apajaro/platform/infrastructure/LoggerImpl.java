package com.apajaro.platform.infrastructure;

import com.apajaro.platform.application.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoggerImpl implements Logger {
    @Override
    public void info(String message) {
        log.info(message);
    }

    @Override
    public void error(String message) {
        log.error(message);
    }

    @Override
    public void error(String message, Exception exception) {
        log.error(message, exception);
    }

}
