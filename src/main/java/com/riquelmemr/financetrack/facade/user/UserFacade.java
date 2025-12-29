package com.riquelmemr.financetrack.facade.user;

import com.riquelmemr.financetrack.dto.request.CreateUserRequest;
import com.riquelmemr.financetrack.dto.response.CreateUserResponse;

public interface UserFacade {
    CreateUserResponse create(CreateUserRequest createUserRequest);
}
