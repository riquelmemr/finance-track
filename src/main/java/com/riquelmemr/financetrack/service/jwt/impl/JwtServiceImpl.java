package com.riquelmemr.financetrack.service.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.riquelmemr.financetrack.config.JwtConfig;
import com.riquelmemr.financetrack.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtConfig jwtConfig;

    @Override
    public String generateToken(String username) {
        try {
            return JWT.create()
                    .withIssuer(jwtConfig.getIssuer())
                    .withSubject(username)
                    .withExpiresAt(getExpirationDate())
                    .sign(jwtConfig.algorithm());
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("An error occurred when generate token: ", exception);
        }
    }

    @Override
    public String getSubjectFromToken(String token) {
        try {
            return JWT.require(jwtConfig.algorithm())
                    .withIssuer(jwtConfig.getIssuer())
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token invalid or expired.");
        }
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

    private Instant getExpirationDate() {
        return Instant.now().plusSeconds(jwtConfig.getExpiration());
    }
}
