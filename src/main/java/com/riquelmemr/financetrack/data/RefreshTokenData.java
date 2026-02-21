package com.riquelmemr.financetrack.data;

import com.riquelmemr.financetrack.model.RefreshTokenModel;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder(setterPrefix = "with")
public class RefreshTokenData {
    private String tokenValue;
    private Instant expiresAt;
    private RefreshTokenModel refreshToken;
}
