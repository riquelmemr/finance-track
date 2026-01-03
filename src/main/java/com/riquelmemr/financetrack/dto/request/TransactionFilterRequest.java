package com.riquelmemr.financetrack.dto.request;

import com.riquelmemr.financetrack.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionFilterRequest {
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private TransactionType type;
    private String categoryCode;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
}
