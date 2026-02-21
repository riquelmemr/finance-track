package com.riquelmemr.financetrack.context.email.impl;

import com.riquelmemr.financetrack.context.email.AbstractEmailContext;
import com.riquelmemr.financetrack.data.email.AccountVerificationEmailData;
import com.riquelmemr.financetrack.enums.EmailTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccountVerificationEmailContext extends AbstractEmailContext<AccountVerificationEmailData> {

    private static final String VERIFY_ACCOUNT_END_POINT = "/users/verify-account?token=";

    @Override
    public String getHtmlContent(AccountVerificationEmailData data) {
        String verificationLink = data.getBaseUrl() + VERIFY_ACCOUNT_END_POINT + data.getVerificationToken();

        try {
            String htmlContent = loadHtmlContent(getEmailTemplate().getTemplateName());

            return htmlContent
                    .replace("${name}", data.getName())
                    .replace("${verification_link}", verificationLink);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load email template", e);
        }
    }

    @Override
    public String getTo(AccountVerificationEmailData data) {
        return data.getTo();
    }

    @Override
    public String getSubject() {
        return getEmailTemplate().getSubject();
    }

    @Override
    public EmailTemplate getEmailTemplate() {
        return EmailTemplate.ACCOUNT_VERIFICATION;
    }
}
