package com.riquelmemr.financetrack.data;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder(setterPrefix = "with")
public class TokenData {
    private String tokenValue;
    private Instant expiresAt;
}
