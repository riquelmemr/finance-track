package com.riquelmemr.financetrack.specification;

import org.springframework.data.jpa.domain.Specification;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public abstract class AbstractSpecification<T, F> implements SpecificationFilter<T, F> {

    protected Specification<T> equal(String field, Object value) {
        return (root, query, cb) ->
                isNull(value) ? null : cb.equal(root.get(field), value);
    }

    protected Specification<T> equal(String field, String subfield, Object value) {
        return (root, query, cb) ->
                isNull(value) ? null : cb.equal(root.get(field).get(subfield), value);
    }

    protected Specification<T> like(String field, String value) {
        return (root, query, cb) ->
                (isNull(value) || value.isBlank()) ? null : cb.like(
                        cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }

    protected <V extends Comparable<? super V>> Specification<T> greaterThanOrEqualTo(String field, V value) {
        return (root, query, cb) ->
                isNull(value) ? null : cb.greaterThanOrEqualTo(root.get(field), value);
    }

    protected <V extends Comparable<? super V>> Specification<T> lessThanOrEqual(String field, V value) {
        return (root, query, cb) ->
                isNull(value) ? null : cb.lessThanOrEqualTo(root.get(field), value);
    }

    protected <V extends Comparable<? super V>> Specification<T> between(String field, V start, V end) {
        return (root, query, cb) -> {
            if (isNull(start) && isNull(end)) {
                return null;
            }

            if (nonNull(start) && nonNull(end)) {
                return cb.between(root.get(field), start, end);
            }

            if (nonNull(start)) {
                return cb.greaterThanOrEqualTo(root.get(field), start);
            }

            return cb.lessThanOrEqualTo(root.get(field), end);
        };
    }
}
