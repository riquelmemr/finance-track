package com.riquelmemr.financetrack.facade.auth;

import com.riquelmemr.financetrack.data.AuthenticationResult;
import com.riquelmemr.financetrack.dto.request.AuthRequest;
import com.riquelmemr.financetrack.dto.request.RegisterUserRequest;
import com.riquelmemr.financetrack.dto.response.UserResponse;

public interface AuthFacade {

    AuthenticationResult authenticate(AuthRequest authRequest);

    UserResponse register(RegisterUserRequest request);

    void logout();

    AuthenticationResult refresh(String refreshToken);
}
