package com.riquelmemr.financetrack.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String name;
    private String description;
}
