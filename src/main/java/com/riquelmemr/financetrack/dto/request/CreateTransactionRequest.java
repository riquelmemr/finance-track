package com.riquelmemr.financetrack.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateTransactionRequest {

    @Positive
    private Double amount;

    @NotBlank
    private String type;

    @NotBlank
    private String categoryCode;

    private String description;
}
