package com.riquelmemr.financetrack.factory.impl;

import com.riquelmemr.financetrack.context.email.EmailContext;
import com.riquelmemr.financetrack.enums.EmailTemplate;
import com.riquelmemr.financetrack.factory.EmailContextFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailContextFactoryImpl implements EmailContextFactory {

    private final List<EmailContext<?>> emailContexts;

    @Override
    @SuppressWarnings("unchecked")
    public <T> EmailContext<T> getEmailContext(EmailTemplate emailTemplate) {
        return emailContexts.stream()
                .filter(c -> c.getEmailTemplate().equals(emailTemplate))
                .findFirst()
                .map(c -> (EmailContext<T>) c)
                .orElseThrow(() -> new RuntimeException("Email context not found for template " + emailTemplate));
    }
}
