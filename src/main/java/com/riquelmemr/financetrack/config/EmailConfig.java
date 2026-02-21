package com.riquelmemr.financetrack.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class EmailConfig {

    @Value("${mail.from}")
    private String from;

    @Value("${mail.sender-name}")
    private String senderName;
}
