package com.riquelmemr.financetrack.utils;

import java.util.UUID;

public class TokenGeneratorUtils {

    private TokenGeneratorUtils() {}

    public static String generateToken() {
        return UUID.randomUUID() + UUID.randomUUID().toString();
    }

}
