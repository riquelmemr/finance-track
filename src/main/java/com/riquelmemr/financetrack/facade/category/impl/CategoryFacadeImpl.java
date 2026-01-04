package com.riquelmemr.financetrack.facade.category.impl;

import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.dto.response.CategoryResponse;
import com.riquelmemr.financetrack.facade.category.CategoryFacade;
import com.riquelmemr.financetrack.model.CategoryModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.service.category.CategoryService;
import com.riquelmemr.financetrack.service.session.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryFacadeImpl implements CategoryFacade {

    private final CategoryService categoryService;
    private final SessionService sessionService;
    private final Converter<CategoryModel, CategoryResponse> categoryResponseConverter;

    @Override
    public CategoryResponse create(CreateCategoryRequest request) {
        UserModel user = sessionService.getCurrentUser();
        CategoryModel categoryModel = categoryService.create(request, user);
        return categoryResponseConverter.convert(categoryModel);
    }

    @Override
    public List<CategoryResponse> findAll(int page, int pageSize) {
        UserModel user = sessionService.getCurrentUser();
        List<CategoryModel> categories = categoryService.findAllByUser(user, page, pageSize);
        return categories.stream().map(categoryResponseConverter::convert).toList();
    }
}
