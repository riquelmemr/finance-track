package com.riquelmemr.financetrack.service.accesstoken;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.riquelmemr.financetrack.model.AccessTokenModel;

public interface AccessTokenService {

    AccessTokenModel generateToken(String username);

    DecodedJWT validateAndDecodedToken(String token);
}
