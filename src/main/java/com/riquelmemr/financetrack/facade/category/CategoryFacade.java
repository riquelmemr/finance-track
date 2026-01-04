package com.riquelmemr.financetrack.facade.category;

import com.riquelmemr.financetrack.dto.request.CategoryFilterRequest;
import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.dto.response.CategoryPageResponse;
import com.riquelmemr.financetrack.dto.response.CategoryResponse;

public interface CategoryFacade {

    CategoryResponse create(CreateCategoryRequest request);

    CategoryPageResponse findAll(CategoryFilterRequest filterRequest, int page, int pageSize);

}
