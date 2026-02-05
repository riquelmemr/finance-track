package com.riquelmemr.financetrack.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
public class AuthenticationData {
    private TokenData accessToken;
    private TokenData refreshToken;
}
