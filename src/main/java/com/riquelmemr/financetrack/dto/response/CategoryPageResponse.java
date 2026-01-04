package com.riquelmemr.financetrack.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryPageResponse extends PageDetailsResponse {
    private List<CategoryResponse> categories;
}
