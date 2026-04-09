package com.riquelmemr.financetrack.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AverageDailyExpenseResponse {

    private BigDecimal averageDailyExpense;

    public AverageDailyExpenseResponse(BigDecimal averageDailyExpense) {
        this.averageDailyExpense = averageDailyExpense;
    }
}
