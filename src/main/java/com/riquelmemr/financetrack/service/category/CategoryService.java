package com.riquelmemr.financetrack.service.category;

import com.riquelmemr.financetrack.dto.request.CategoryFilterRequest;
import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.dto.request.UpdateCategoryRequest;
import com.riquelmemr.financetrack.model.CategoryModel;
import com.riquelmemr.financetrack.model.UserModel;
import org.springframework.data.domain.Page;

public interface CategoryService {

    CategoryModel create(CreateCategoryRequest request, UserModel user);

    CategoryModel findByCode(String code, UserModel user);

    Page<CategoryModel> findAll(UserModel user, CategoryFilterRequest filterRequest, int page, int pageSize);

    CategoryModel findById(Long id, UserModel user);

    CategoryModel update(Long id, UpdateCategoryRequest request, UserModel user);

    void deleteById(Long id, UserModel user);
}
