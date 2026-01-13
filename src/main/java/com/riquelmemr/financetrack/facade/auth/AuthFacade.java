package com.riquelmemr.financetrack.facade.auth;

import com.riquelmemr.financetrack.dto.request.AuthRequest;
import com.riquelmemr.financetrack.dto.request.RegisterUserRequest;
import com.riquelmemr.financetrack.dto.response.AuthResponse;
import com.riquelmemr.financetrack.dto.response.UserResponse;

public interface AuthFacade {

    AuthResponse authenticate(AuthRequest authRequest);

    UserResponse register(RegisterUserRequest request);

    void logout();
}
