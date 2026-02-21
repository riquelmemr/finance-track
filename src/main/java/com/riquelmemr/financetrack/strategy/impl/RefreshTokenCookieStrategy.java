package com.riquelmemr.financetrack.strategy.impl;

import com.riquelmemr.financetrack.data.RefreshTokenData;
import com.riquelmemr.financetrack.enums.Cookie;
import com.riquelmemr.financetrack.strategy.CookieStrategy;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class RefreshTokenCookieStrategy implements CookieStrategy<RefreshTokenData> {

    @Override
    public ResponseCookie create(RefreshTokenData data) {
        long maxAge = Duration.between(Instant.now(), data.getExpiresAt()).getSeconds();

        return ResponseCookie.from(getCookie().getKey(), data.getTokenValue())
                .httpOnly(true)
                .secure(false)
                .maxAge(maxAge)
                .path("/")
                .sameSite("sameSite")
                .build();
    }

    @Override
    public Cookie getCookie() {
        return Cookie.REFRESH_TOKEN;
    }
}
