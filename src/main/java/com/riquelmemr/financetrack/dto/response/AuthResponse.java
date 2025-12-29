package com.riquelmemr.financetrack.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AuthResponse {
    private String accessToken;
    private Long expiresIn;
}
