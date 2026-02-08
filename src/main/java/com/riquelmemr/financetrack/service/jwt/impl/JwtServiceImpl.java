package com.riquelmemr.financetrack.service.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.riquelmemr.financetrack.config.JwtConfig;
import com.riquelmemr.financetrack.data.GeneratedAccessToken;
import com.riquelmemr.financetrack.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtConfig jwtConfig;

    @Override
    public GeneratedAccessToken generateToken(String username) {
        try {
            String token = JWT.create()
                    .withIssuer(jwtConfig.getIssuer())
                    .withSubject(username)
                    .withExpiresAt(getExpiration())
                    .sign(jwtConfig.algorithm());

            DecodedJWT decoded = JWT.decode(token);

            return new GeneratedAccessToken(token, decoded.getExpiresAtAsInstant());
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("An error occurred when generate token: ", exception);
        }
    }

    @Override
    public Instant getRefreshTokenExpiration() {
        return Instant.now().plusSeconds(jwtConfig.getRefreshExpiration());
    }

    @Override
    public DecodedJWT validateToken(String token) {
        try {
            return JWT.require(jwtConfig.algorithm())
                    .withIssuer(jwtConfig.getIssuer())
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token invalid or expired.");
        }
    }

    private Instant getExpiration() {
        return Instant.now().plusSeconds(jwtConfig.getAccessExpiration());
    }
}
