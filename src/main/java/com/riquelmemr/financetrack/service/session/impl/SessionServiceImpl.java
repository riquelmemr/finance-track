package com.riquelmemr.financetrack.service.session.impl;

import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.security.userdetails.UserDetailsImpl;
import com.riquelmemr.financetrack.service.session.SessionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    @Override
    public UserModel getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) getCurrentAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    @Override
    public String getCurrentUsername() {
        return getCurrentAuthentication().getName();
    }

    @Override
    public Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
