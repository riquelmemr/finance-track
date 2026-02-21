package com.riquelmemr.financetrack.facade.user;

import com.riquelmemr.financetrack.dto.response.UserResponse;

public interface UserFacade {

    UserResponse getMe();

    void resendAccountVerification(String email);

    void verifyAccount(String token);
}
