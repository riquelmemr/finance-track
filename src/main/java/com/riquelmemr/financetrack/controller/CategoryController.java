package com.riquelmemr.financetrack.controller;

import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.dto.response.CategoryResponse;
import com.riquelmemr.financetrack.dto.response.DataWrapperResponse;
import com.riquelmemr.financetrack.facade.category.CategoryFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController extends BaseController {

    private static final String CATEGORY_CREATED_SUCCESSFULLY_MESSAGE = "Category created successfully.";
    private static final String CATEGORIES_FOUND_SUCCESSFULLY_MESSAGE = "Categories found with successfully.";

    private final CategoryFacade categoryFacade;

    @Secured("ROLE_BASIC")
    @PostMapping
    public ResponseEntity<DataWrapperResponse<CategoryResponse>> create(@Valid @RequestBody CreateCategoryRequest request) {
        CategoryResponse response = categoryFacade.create(request);
        return handleResponse(HttpStatus.OK, response, CATEGORY_CREATED_SUCCESSFULLY_MESSAGE);
    }

    @Secured("ROLE_BASIC")
    @GetMapping
    public ResponseEntity<DataWrapperResponse<List<CategoryResponse>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "25") int pageSize
    ) {
        List<CategoryResponse> response = categoryFacade.findAll(page, pageSize);
        return handleResponse(HttpStatus.OK, response, CATEGORIES_FOUND_SUCCESSFULLY_MESSAGE);
    }
}
