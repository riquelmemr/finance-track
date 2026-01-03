package com.riquelmemr.financetrack.specification;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationFilter<T, F> {
    Specification<T> toSpecification(F data);
}
