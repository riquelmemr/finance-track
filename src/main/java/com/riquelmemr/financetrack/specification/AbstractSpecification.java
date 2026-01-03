package com.riquelmemr.financetrack.specification;

import org.springframework.data.jpa.domain.Specification;

public abstract class AbstractSpecification<T, F> implements SpecificationFilter<T, F> {

    protected Specification<T> equal(String field, Object value) {
        return (root, query, cb) ->
                value == null ? null : cb.equal(root.get(field), value);
    }

    protected Specification<T> equal(String field, String subfield, Object value) {
        return (root, query, cb) ->
                value == null ? null : cb.equal(root.get(field).get(subfield), value);
    }

    protected <V extends Comparable<? super V>> Specification<T> greaterThanOrEqualTo(String field, V value) {
        return (root, query, cb) ->
                value == null ? null : cb.greaterThanOrEqualTo(root.get(field), value);
    }

    protected <V extends Comparable<? super V>> Specification<T> lessThanOrEqual(String field, V value) {
        return (root, query, cb) ->
                value == null ? null : cb.lessThanOrEqualTo(root.get(field), value);
    }

    protected <V extends Comparable<? super V>> Specification<T> between(String field, V start, V end) {
        return (root, query, cb) -> {
            if (start == null && end == null) {
                return null;
            }

            if (start != null && end != null) {
                return cb.between(root.get(field), start, end);
            }

            if (start != null) {
                return cb.greaterThanOrEqualTo(root.get(field), start);
            }

            return cb.lessThanOrEqualTo(root.get(field), end);
        };
    }
}
