package com.riquelmemr.financetrack.enums;

import lombok.Getter;

@Getter
public enum EmailTemplate {

    ACCOUNT_VERIFICATION("account_verification_template.html", "Confirme sua conta"),
    PASSWORD_RESET("password_reset_template.html", "Redefinição de senha");

    private final String templateName;
    private final String subject;

    EmailTemplate(String templateName, String subject) {
        this.templateName = templateName;
        this.subject = subject;
    }
}
