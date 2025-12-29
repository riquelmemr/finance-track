package com.riquelmemr.financetrack.dto.response;

import com.riquelmemr.financetrack.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionResponse {
    private Long id;
    private String category;
    private String description;
    private TransactionType type;
    private BigDecimal amount;
    private String date;
}
