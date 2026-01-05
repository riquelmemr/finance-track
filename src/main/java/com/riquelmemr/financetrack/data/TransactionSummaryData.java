package com.riquelmemr.financetrack.data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionSummaryData {
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal balance;
    private Long count;

    public TransactionSummaryData(BigDecimal income, BigDecimal expense, Long count) {
        this.income = income;
        this.expense = expense;
        this.balance = income.subtract(expense);
        this.count = count;
    }
}
