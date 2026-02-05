package com.riquelmemr.financetrack.service.refreshtoken;

import com.riquelmemr.financetrack.data.RefreshTokenData;
import com.riquelmemr.financetrack.model.RefreshTokenModel;
import com.riquelmemr.financetrack.model.UserModel;

public interface RefreshTokenService {

    RefreshTokenData generateToken(UserModel user);

    void revokeToken(RefreshTokenModel refreshToken);

    RefreshTokenModel validateToken(String refreshToken);

}
