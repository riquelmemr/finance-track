package com.riquelmemr.financetrack.controller;

import com.riquelmemr.financetrack.dto.request.CategoryFilterRequest;
import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.dto.response.CategoryPageResponse;
import com.riquelmemr.financetrack.dto.response.CategoryResponse;
import com.riquelmemr.financetrack.dto.response.DataWrapperResponse;
import com.riquelmemr.financetrack.facade.category.CategoryFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController extends BaseController {

    private static final String CATEGORY_CREATED_SUCCESSFULLY_MESSAGE = "Category created with successfully.";
    private static final String CATEGORY_FOUND_SUCESSFULLY_MESSAGE = "Category found with successfully.";
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
    public ResponseEntity<DataWrapperResponse<CategoryPageResponse>> findAll(
            CategoryFilterRequest filter,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "25") int pageSize
    ) {
        CategoryPageResponse response = categoryFacade.findAll(filter, page, pageSize);
        return handleResponse(HttpStatus.OK, response, CATEGORIES_FOUND_SUCCESSFULLY_MESSAGE);
    }

    @Secured("ROLE_BASIC")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryFacade.deleteById(id);
        return handleNoContentResponse();
    }

    @Secured("ROLE_BASIC")
    @GetMapping("/{id}")
    public ResponseEntity<DataWrapperResponse<CategoryResponse>> findById(@PathVariable Long id) {
        CategoryResponse response = categoryFacade.findById(id);
        return handleResponse(HttpStatus.OK, response, CATEGORY_FOUND_SUCESSFULLY_MESSAGE);
    }
}
