package com.riquelmemr.financetrack.data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpenseByCategoryData {
    private String category;
    private BigDecimal total;
    private BigDecimal percentage;

    public ExpenseByCategoryData(String category, BigDecimal total) {
        this.category = category;
        this.total = total;
    }
}
