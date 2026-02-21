package com.riquelmemr.financetrack.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class GeneratedAccessToken {
    private String tokenValue;
    private Instant expiresAt;
}
