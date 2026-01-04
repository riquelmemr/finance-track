package com.riquelmemr.financetrack.repository;

import com.riquelmemr.financetrack.model.CategoryModel;
import com.riquelmemr.financetrack.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CategoryRepository
        extends JpaRepository<CategoryModel, Long>, JpaSpecificationExecutor<CategoryModel> {
    
    Optional<CategoryModel> findByCodeAndUser(String code, UserModel user);

    Optional<CategoryModel> findByIdAndUser(Long id, UserModel user);
}
