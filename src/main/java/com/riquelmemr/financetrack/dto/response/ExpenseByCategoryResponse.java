package com.riquelmemr.financetrack.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpenseByCategoryResponse {
    private String category;
    private BigDecimal total;
    private BigDecimal percentage;
}
