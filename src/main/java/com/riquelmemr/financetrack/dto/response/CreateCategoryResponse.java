package com.riquelmemr.financetrack.dto.response;

import lombok.Data;

@Data
public class CreateCategoryResponse {
    private String code;
    private String name;
    private String description;
}
