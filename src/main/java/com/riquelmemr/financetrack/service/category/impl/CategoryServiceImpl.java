package com.riquelmemr.financetrack.service.category.impl;

import com.riquelmemr.financetrack.dto.request.CategoryFilterRequest;
import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.dto.request.UpdateCategoryRequest;
import com.riquelmemr.financetrack.exception.ModelAlreadyExistsException;
import com.riquelmemr.financetrack.exception.ModelNotFoundException;
import com.riquelmemr.financetrack.model.CategoryModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.CategoryRepository;
import com.riquelmemr.financetrack.service.category.CategoryService;
import com.riquelmemr.financetrack.specification.impl.CategorySpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategorySpecification categorySpecification;

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
    public CategoryModel findByCode(String code, UserModel user) {
        return categoryRepository.findByCodeAndUser(code, user).orElseThrow(() ->
                new ModelNotFoundException("Category with code " + code + " not found.")
        );
    }

    @Override
    public Page<CategoryModel> findAll(UserModel user, CategoryFilterRequest filterRequest, int page, int pageSize) {
        filterRequest.setUser(user);

        Specification<CategoryModel> spec = categorySpecification.toSpecification(filterRequest);
        PageRequest pageRequest = PageRequest.of(page, pageSize);

        return categoryRepository.findAll(spec, pageRequest);
    }

    @Override
    public CategoryModel findById(Long id, UserModel user) {
        return categoryRepository.findByIdAndUser(id, user).orElseThrow(() ->
                new ModelNotFoundException("Category not found with ID [" + id + "]")
        );
    }

    @Override
    public CategoryModel update(Long id, UpdateCategoryRequest request, UserModel user) {
        CategoryModel category = findById(id, user);

        if (nonNull(request.getCode())) {
            category.setCode(request.getCode());
        }

        if (nonNull(request.getDescription())) {
            category.setDescription(request.getDescription());
        }

        if (nonNull(request.getName())) {
            category.setName(request.getName());
        }

        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id, UserModel user) {
        CategoryModel category = findById(id, user);
        categoryRepository.delete(category);
    }
}
