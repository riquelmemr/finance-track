package com.riquelmemr.financetrack.facade.category.impl;

import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.dto.response.CreateCategoryResponse;
import com.riquelmemr.financetrack.facade.category.CategoryFacade;
import com.riquelmemr.financetrack.model.CategoryModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.service.category.CategoryService;
import com.riquelmemr.financetrack.service.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryFacadeImpl implements CategoryFacade {

    private final CategoryService categoryService;
    private final SessionService sessionService;
    private final Converter<CategoryModel, CreateCategoryResponse> createCategoryResponseConverter;

    @Override
    public CreateCategoryResponse create(CreateCategoryRequest request) {
        UserModel userModel = sessionService.getCurrentUser();
        CategoryModel categoryModel = categoryService.create(request, userModel);
        return createCategoryResponseConverter.convert(categoryModel);
    }
}
