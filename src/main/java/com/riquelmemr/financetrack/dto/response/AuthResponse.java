package com.riquelmemr.financetrack.dto.response;

import lombok.*;

@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private Long expiresIn;
}
