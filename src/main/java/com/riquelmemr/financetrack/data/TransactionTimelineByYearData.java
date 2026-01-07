package com.riquelmemr.financetrack.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionTimelineByYearData {
    private Integer year;
    private BigDecimal income;
    private BigDecimal expense;
}
