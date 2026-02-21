package com.riquelmemr.financetrack.service.user;

import com.riquelmemr.financetrack.model.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserModel findByUsername(String username);

    void resendAccountVerification(String email);

    void verifyAccount(String token);
}
