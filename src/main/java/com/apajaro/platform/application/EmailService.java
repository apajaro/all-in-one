package com.apajaro.platform.application;

import java.util.Map;

public interface EmailService {
    void sendEmail(String to, String subject, String fromEmail, String fromName, Map<String, Object> variables, String emailTemplate);
}
