package com.riquelmemr.financetrack.converter;

import com.riquelmemr.financetrack.data.AuthenticationData;
import com.riquelmemr.financetrack.data.AuthenticationResult;
import org.springframework.http.ResponseCookie;

public interface AuthenticationTokenResultConverter {
    AuthenticationResult convert(AuthenticationData authenticationData, ResponseCookie cookie);
}
