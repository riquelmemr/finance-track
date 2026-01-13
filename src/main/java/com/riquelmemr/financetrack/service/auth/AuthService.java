package com.riquelmemr.financetrack.service.auth;

import com.riquelmemr.financetrack.dto.request.RegisterUserRequest;
import com.riquelmemr.financetrack.model.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    UserModel register(RegisterUserRequest request, UserModel user);

    void logout(UserModel user);
}
