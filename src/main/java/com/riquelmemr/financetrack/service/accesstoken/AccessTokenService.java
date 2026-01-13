package com.riquelmemr.financetrack.service.accesstoken;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.riquelmemr.financetrack.model.AccessTokenModel;
import com.riquelmemr.financetrack.model.UserModel;

public interface AccessTokenService {

    AccessTokenModel generateToken(String username);

    DecodedJWT validateToken(String token);

    void deleteToken(UserModel user);
}
