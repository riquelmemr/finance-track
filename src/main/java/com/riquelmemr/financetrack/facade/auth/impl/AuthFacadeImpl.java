package com.riquelmemr.financetrack.facade.auth.impl;

import com.riquelmemr.financetrack.converter.data.AuthenticationTokenResultConverterImpl;
import com.riquelmemr.financetrack.data.AuthenticationResult;
import com.riquelmemr.financetrack.data.AuthenticationData;
import com.riquelmemr.financetrack.data.RefreshTokenData;
import com.riquelmemr.financetrack.dto.request.AuthRequest;
import com.riquelmemr.financetrack.dto.request.RegisterUserRequest;
import com.riquelmemr.financetrack.dto.response.UserResponse;
import com.riquelmemr.financetrack.enums.Cookie;
import com.riquelmemr.financetrack.facade.auth.AuthFacade;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.service.auth.AuthService;
import com.riquelmemr.financetrack.service.cookie.CookieService;
import com.riquelmemr.financetrack.service.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade {

    private final AuthService authService;
    private final CookieService cookieService;
    private final Converter<UserModel, UserResponse> userResponseConverter;
    private final AuthenticationTokenResultConverterImpl authenticationTokenResultConverter;
    private final SessionService sessionService;

    @Override
    public AuthenticationResult authenticate(AuthRequest authRequest) {
        AuthenticationData authenticationData = authService.authenticate(authRequest);
        return authenticationTokenResultConverter.convert(authenticationData, getCookie(authenticationData));
    }

    @Override
    public UserResponse register(RegisterUserRequest request) {
        UserModel user = authService.register(request);
        return userResponseConverter.convert(user);
    }

    @Override
    public void logout() {
        Authentication authentication = sessionService.getCurrentAuthentication();
        String token = (String) authentication.getDetails();
        authService.logout(token);
    }

    @Override
    public AuthenticationResult refresh(String refreshToken) {
        AuthenticationData authenticationData = authService.refresh(refreshToken);
        return authenticationTokenResultConverter.convert(authenticationData, getCookie(authenticationData));
    }

    private ResponseCookie getCookie(AuthenticationData data) {
        return cookieService.create(
                Cookie.REFRESH_TOKEN,
                RefreshTokenData.builder()
                        .withToken(data.getRefreshToken().getTokenValue())
                        .withExpiresAt(data.getRefreshToken().getExpiresAt())
                        .build());
    }
}
