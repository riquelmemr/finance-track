package com.riquelmemr.financetrack.service.category.impl;

import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.exception.ModelAlreadyExistsException;
import com.riquelmemr.financetrack.model.CategoryModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.CategoryRepository;
import com.riquelmemr.financetrack.service.category.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryModel create(CreateCategoryRequest request, UserModel user) {
        Optional<CategoryModel> categoryModelOpt = categoryRepository.findByCodeAndUserId(request.getCode(), user.getId());

        if (categoryModelOpt.isPresent()) {
            throw new ModelAlreadyExistsException("Category already exists with code " + request.getCode());
        }

        CategoryModel category = CategoryModel.builder()
                .withCode(request.getCode())
                .withName(request.getName())
                .withUser(user)
                .withDescription(request.getDescription())
                .build();

        return categoryRepository.save(category);
    }

    @Override
    public Optional<CategoryModel> findByCodeAndUserId(String code, Long userId) {
        return categoryRepository.findByCodeAndUserId(code, userId);
    }
}
