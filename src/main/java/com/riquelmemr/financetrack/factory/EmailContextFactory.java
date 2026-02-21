package com.riquelmemr.financetrack.factory;

import com.riquelmemr.financetrack.context.email.EmailContext;
import com.riquelmemr.financetrack.enums.EmailTemplate;

public interface EmailContextFactory {
    <T> EmailContext<T> getEmailContext(EmailTemplate emailTemplate);
}
