package com.riquelmemr.financetrack.service.auth;

import com.riquelmemr.financetrack.data.AuthenticationData;
import com.riquelmemr.financetrack.dto.request.AuthRequest;
import com.riquelmemr.financetrack.dto.request.RegisterUserRequest;
import com.riquelmemr.financetrack.model.UserModel;

public interface AuthService {

    UserModel register(RegisterUserRequest request, UserModel user);

    UserModel register(RegisterUserRequest request);

    AuthenticationData authenticate(AuthRequest authRequest);

    void logout(String token);

    AuthenticationData refresh(String refreshToken);
}
