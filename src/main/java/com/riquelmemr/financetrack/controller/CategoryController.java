package com.riquelmemr.financetrack.controller;

import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.dto.response.CreateCategoryResponse;
import com.riquelmemr.financetrack.dto.response.DataWrapperResponse;
import com.riquelmemr.financetrack.facade.category.CategoryFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController extends BaseController {

    private static final String CREATE_CATEGORY_SUCCESSFULLY_MESSAGE = "Category created successfully.";

    private final CategoryFacade categoryFacade;

    @Secured("ROLE_BASIC")
    @PostMapping
    public ResponseEntity<DataWrapperResponse<CreateCategoryResponse>> create(@Valid @RequestBody CreateCategoryRequest request) {
        CreateCategoryResponse response = categoryFacade.create(request);
        return handleResponse(HttpStatus.OK, response, CREATE_CATEGORY_SUCCESSFULLY_MESSAGE);
    }
}
