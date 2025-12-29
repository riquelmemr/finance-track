package com.riquelmemr.financetrack.facade.auth;

import com.riquelmemr.financetrack.dto.request.AuthRequest;
import com.riquelmemr.financetrack.dto.response.AuthResponse;

public interface AuthFacade {
    AuthResponse authenticate(AuthRequest authRequest);
}
