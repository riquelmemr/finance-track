package com.riquelmemr.financetrack.service.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;

public interface JwtService {

    String generateToken(String username);

    DecodedJWT decodeToken(String token);

    DecodedJWT validateToken(String token);

    Instant getRefreshTokenExpiration();

}
