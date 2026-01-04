package com.riquelmemr.financetrack.service.category.impl;

import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.exception.ModelAlreadyExistsException;
import com.riquelmemr.financetrack.model.CategoryModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.CategoryRepository;
import com.riquelmemr.financetrack.service.category.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryModel create(CreateCategoryRequest request, UserModel user) {
        Optional<CategoryModel> categoryModelOpt = categoryRepository.findByCodeAndUser(request.getCode(), user);

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
    public Optional<CategoryModel> findByCodeAndUser(String code, UserModel user) {
        return categoryRepository.findByCodeAndUser(code, user);
    }

    @Override
    public List<CategoryModel> findAllByUser(UserModel user, int page, int pageSize) {
        return categoryRepository.findAllByUser(user, PageRequest.of(page, pageSize));
    }
}
