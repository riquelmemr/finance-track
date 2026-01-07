package com.riquelmemr.financetrack.data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionTimelineData {
    private String period;
    private BigDecimal income;
    private BigDecimal expense;
}
