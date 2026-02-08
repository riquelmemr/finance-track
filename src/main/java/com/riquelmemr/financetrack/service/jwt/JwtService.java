package com.riquelmemr.financetrack.service.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.riquelmemr.financetrack.data.GeneratedAccessToken;

import java.time.Instant;

public interface JwtService {

    GeneratedAccessToken generateToken(String username);

    DecodedJWT validateToken(String token);

    Instant getRefreshTokenExpiration();

}
