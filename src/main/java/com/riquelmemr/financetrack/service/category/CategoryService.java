package com.riquelmemr.financetrack.service.category;

import com.riquelmemr.financetrack.dto.request.CategoryFilterRequest;
import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.model.CategoryModel;
import com.riquelmemr.financetrack.model.UserModel;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CategoryService {

    CategoryModel create(CreateCategoryRequest request, UserModel user);

    Optional<CategoryModel> findByCodeAndUser(String code, UserModel userId);

    Page<CategoryModel> findAll(UserModel user, CategoryFilterRequest filterRequest, int page, int pageSize);

}
