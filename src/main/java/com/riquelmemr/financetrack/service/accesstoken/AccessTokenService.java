package com.riquelmemr.financetrack.service.accesstoken;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.riquelmemr.financetrack.data.AuthenticationData;
import com.riquelmemr.financetrack.model.AccessTokenModel;
import com.riquelmemr.financetrack.model.RefreshTokenModel;

import java.time.Instant;

public interface AccessTokenService {

    AuthenticationData generateToken(String username);

    DecodedJWT validateToken(String rawToken);

    AccessTokenModel findByToken(String rawToken);

    void revokeToken(AccessTokenModel accessTokenModel);

    void revokeAllByRefreshToken(RefreshTokenModel refreshTokenModel);

    void deleteExpiredAndRevoked(Instant threshold);
}
