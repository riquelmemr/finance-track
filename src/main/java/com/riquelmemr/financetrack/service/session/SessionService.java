package com.riquelmemr.financetrack.service.session;

import com.riquelmemr.financetrack.model.UserModel;
import org.springframework.security.core.Authentication;

public interface SessionService {

    UserModel getCurrentUser();

    String getCurrentUsername();

    Authentication getCurrentAuthentication();
}
