package com.riquelmemr.financetrack.service.category;

import com.riquelmemr.financetrack.dto.request.CreateCategoryRequest;
import com.riquelmemr.financetrack.model.CategoryModel;
import com.riquelmemr.financetrack.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryModel create(CreateCategoryRequest request, UserModel user);

    Optional<CategoryModel> findByCodeAndUser(String code, UserModel userId);

    List<CategoryModel> findAllByUser(UserModel user, int page, int pageSize);

}
