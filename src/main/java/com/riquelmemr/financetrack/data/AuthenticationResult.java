package com.riquelmemr.financetrack.data;

import com.riquelmemr.financetrack.dto.response.AuthResponse;
import lombok.Data;
import org.springframework.http.ResponseCookie;

@Data
public class AuthenticationResult {
    AuthResponse authResponse;
    ResponseCookie refreshTokenCookie;
}
