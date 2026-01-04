package com.riquelmemr.financetrack.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionPageResponse extends PageDetailsResponse {
    private BigDecimal balance;
    private List<TransactionResponse> transactions;
}
