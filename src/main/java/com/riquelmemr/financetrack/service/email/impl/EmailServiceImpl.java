package com.riquelmemr.financetrack.service.email.impl;

import com.riquelmemr.financetrack.config.EmailConfig;
import com.riquelmemr.financetrack.context.email.EmailContext;
import com.riquelmemr.financetrack.enums.EmailTemplate;
import com.riquelmemr.financetrack.factory.EmailContextFactory;
import com.riquelmemr.financetrack.service.email.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailContextFactory emailContextFactory;
    private final EmailConfig emailConfig;
    private final JavaMailSender mailSender;

    @Override
    public <T> void sendEmail(EmailTemplate emailTemplate, T data) {
        EmailContext<T> emailContext = emailContextFactory.getEmailContext(emailTemplate);

        try {
            log.info("Sending email to {} with template {}", emailContext.getTo(data), emailTemplate);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(emailContext.getTo(data));
            helper.setFrom(emailConfig.getFrom(), emailConfig.getSenderName());
            helper.setSubject(emailContext.getSubject());
            helper.setText(emailContext.getHtmlContent(data), true);

            mailSender.send(message);
            log.info("Email sent successfully to {}", emailContext.getTo(data));
        } catch (Exception e) {
            log.error("Failed to send email to {} with template {}: {}", emailContext.getTo(data), emailTemplate, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
