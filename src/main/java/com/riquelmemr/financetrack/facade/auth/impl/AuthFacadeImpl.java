package com.riquelmemr.financetrack.facade.auth.impl;

import com.riquelmemr.financetrack.dto.request.AuthRequest;
import com.riquelmemr.financetrack.dto.request.RegisterUserRequest;
import com.riquelmemr.financetrack.dto.response.AuthResponse;
import com.riquelmemr.financetrack.dto.response.UserResponse;
import com.riquelmemr.financetrack.facade.auth.AuthFacade;
import com.riquelmemr.financetrack.model.AccessTokenModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.security.userdetails.UserDetailsImpl;
import com.riquelmemr.financetrack.service.accesstoken.AccessTokenService;
import com.riquelmemr.financetrack.service.auth.AuthService;
import com.riquelmemr.financetrack.service.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade {

    private final AuthService authService;
    private final AccessTokenService accessTokenService;
    private final AuthenticationManager authenticationManager;
    private final Converter<UserModel, UserResponse> userResponseConverter;
    private final SessionService sessionService;

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        AccessTokenModel accessTokenModel = accessTokenService.generateToken(userDetails.getUsername());

        return AuthResponse.builder()
                .withAccessToken(accessTokenModel.getToken())
                .withExpiresIn(Duration.between(Instant.now(), accessTokenModel.getExpiresAt()).getSeconds())
                .build();
    }

    @Override
    public UserResponse register(RegisterUserRequest request) {
        UserModel user = authService.register(request, null);
        return userResponseConverter.convert(user);
    }

    @Override
    public void logout() {
        UserModel user = sessionService.getCurrentUser();
        authService.logout(user);
    }
}
