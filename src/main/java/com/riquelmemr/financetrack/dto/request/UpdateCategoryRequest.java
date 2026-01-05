package com.riquelmemr.financetrack.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCategoryRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String name;
    private String description;
}
