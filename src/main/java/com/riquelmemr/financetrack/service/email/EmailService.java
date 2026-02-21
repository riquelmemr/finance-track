package com.riquelmemr.financetrack.service.email;

import com.riquelmemr.financetrack.enums.EmailTemplate;

public interface EmailService {
    <T> void sendEmail(EmailTemplate emailTemplate, T data);
}
