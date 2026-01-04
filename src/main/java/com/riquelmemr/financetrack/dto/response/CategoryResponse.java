package com.riquelmemr.financetrack.dto.response;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
}
