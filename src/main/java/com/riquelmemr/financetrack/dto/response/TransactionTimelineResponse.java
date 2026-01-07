package com.riquelmemr.financetrack.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionTimelineResponse {
    private String period;
    private BigDecimal income;
    private BigDecimal expense;
}
