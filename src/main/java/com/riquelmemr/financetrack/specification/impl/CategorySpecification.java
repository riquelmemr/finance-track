package com.riquelmemr.financetrack.specification.impl;

import com.riquelmemr.financetrack.dto.request.CategoryFilterRequest;
import com.riquelmemr.financetrack.model.CategoryModel;
import com.riquelmemr.financetrack.specification.AbstractSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CategorySpecification extends AbstractSpecification<CategoryModel, CategoryFilterRequest> {

    @Override
    public Specification<CategoryModel> toSpecification(CategoryFilterRequest data) {
        return Specification
                .where(equal("user", data.getUser()))
                .and(like("code", data.getCode()))
                .and(like("name", data.getName()))
                .and(like("description", data.getDescription()));
    }
}
