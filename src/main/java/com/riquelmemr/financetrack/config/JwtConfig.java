package com.riquelmemr.financetrack.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private String secret;
    private String issuer;

    @Value("${jwt.access-token.expiration}")
    private Long accessExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshExpiration;

    public Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }
}
