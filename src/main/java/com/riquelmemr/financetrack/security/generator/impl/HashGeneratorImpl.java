package com.riquelmemr.financetrack.security.generator.impl;

import com.riquelmemr.financetrack.security.generator.HashGenerator;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

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
}
