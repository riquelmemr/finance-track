package com.riquelmemr.financetrack.service.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface JwtService {

    String generateToken(String username);

    String getSubjectFromToken(String token);

    DecodedJWT validateToken(String token);
}
