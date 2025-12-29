package com.riquelmemr.financetrack.facade.category;

import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.dto.response.CreateCategoryResponse;

public interface CategoryFacade {

    CreateCategoryResponse create(CreateCategoryRequest request);

}
