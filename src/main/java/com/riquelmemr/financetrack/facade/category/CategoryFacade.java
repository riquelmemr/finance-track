package com.riquelmemr.financetrack.facade.category;

import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryFacade {

    CategoryResponse create(CreateCategoryRequest request);

    List<CategoryResponse> findAll(int page, int pageSize);

}
