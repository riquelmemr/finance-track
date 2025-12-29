package com.riquelmemr.financetrack.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TransactionPageResponse {
    private BigDecimal balance;
    private List<TransactionResponse> transactions;
    private int page;
    private int pageSize;
}
