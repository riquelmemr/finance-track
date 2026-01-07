package com.riquelmemr.financetrack.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionTimelineByMonthData {
    private Integer year;
    private Integer month;
    private BigDecimal income;
    private BigDecimal expense;
}
