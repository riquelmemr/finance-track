package com.riquelmemr.financetrack.converter.data;

import com.riquelmemr.financetrack.converter.AuthenticationTokenResultConverter;
import com.riquelmemr.financetrack.data.AuthenticationData;
import com.riquelmemr.financetrack.data.AuthenticationResult;
import com.riquelmemr.financetrack.dto.response.AuthResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static com.riquelmemr.financetrack.utils.DateUtils.getSecondsBetweenDates;

@Component
public class AuthenticationTokenResultConverterImpl implements AuthenticationTokenResultConverter {

    public AuthenticationResult convert(AuthenticationData authenticationData, ResponseCookie cookie) {
        AuthenticationResult target = new AuthenticationResult();

        target.setAuthResponse(getAuthResponse(authenticationData));
        target.setRefreshTokenCookie(cookie);

        return target;
    }

    private AuthResponse getAuthResponse(AuthenticationData authenticationData) {
        AuthResponse authResponse = new AuthResponse();

        long expiresIn = getSecondsBetweenDates(Instant.now(), authenticationData.getAccessToken().getExpiresAt());

        authResponse.setAccessToken(authenticationData.getAccessToken().getTokenValue());
        authResponse.setExpiresIn(expiresIn);

        return authResponse;
    }
}
