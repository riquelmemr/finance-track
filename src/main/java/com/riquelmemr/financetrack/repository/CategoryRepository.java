package com.riquelmemr.financetrack.repository;

import com.riquelmemr.financetrack.model.CategoryModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    
    Optional<CategoryModel> findByCodeAndUserId(String code, Long userId);

    List<CategoryModel> findAllByUserId(Long userId, Pageable pageable);
}
