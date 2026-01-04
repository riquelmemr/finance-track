package com.riquelmemr.financetrack.facade.category.impl;

import com.riquelmemr.financetrack.dto.request.CategoryFilterRequest;
import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.dto.response.CategoryPageResponse;
import com.riquelmemr.financetrack.dto.response.CategoryResponse;
import com.riquelmemr.financetrack.facade.category.CategoryFacade;
import com.riquelmemr.financetrack.model.CategoryModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.service.category.CategoryService;
import com.riquelmemr.financetrack.service.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryFacadeImpl implements CategoryFacade {

    private final CategoryService categoryService;
    private final SessionService sessionService;
    private final Converter<CategoryModel, CategoryResponse> categoryResponseConverter;
    private final Converter<Page<CategoryModel>, CategoryPageResponse> categoryPageResponseConverter;

    @Override
    public CategoryResponse create(CreateCategoryRequest request) {
        UserModel user = sessionService.getCurrentUser();
        CategoryModel categoryModel = categoryService.create(request, user);
        return categoryResponseConverter.convert(categoryModel);
    }

    @Override
    public CategoryPageResponse findAll(CategoryFilterRequest filterRequest, int page, int pageSize) {
        UserModel user = sessionService.getCurrentUser();
        Page<CategoryModel> categories = categoryService.findAll(user, filterRequest, page, pageSize);
        return categoryPageResponseConverter.convert(categories);
    }
}
