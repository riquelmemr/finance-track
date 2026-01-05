package com.riquelmemr.financetrack.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionSummaryResponse {
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal balance;
    private Integer transactionsCount;
}
