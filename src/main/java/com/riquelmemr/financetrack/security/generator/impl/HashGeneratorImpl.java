package com.riquelmemr.financetrack.security.generator.impl;

import com.riquelmemr.financetrack.security.generator.HashGenerator;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import static java.util.Objects.isNull;

@Component
public class HashGeneratorImpl implements HashGenerator {

    private static final String PIPE = "|";

    @Override
    public String generate(String value) {
        return hash(value);
    }

    private String hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("An occurred error when generate hash", e);
        }
    }

    private String normalize(String userAgent) {
        if (isNull(userAgent)) return "unknown";

        return userAgent
                .replaceAll("Chrome/[0-9.]+", "Chrome")
                .replaceAll("Safari/[0-9.]+", "Safari")
                .replaceAll("Firefox/[0-9.]+", "Firefox")
                .toLowerCase();
    }
}
