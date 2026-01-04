package com.riquelmemr.financetrack.converter.response;

import com.riquelmemr.financetrack.dto.response.CategoryResponse;
import com.riquelmemr.financetrack.model.CategoryModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryResponseConverter implements Converter<CategoryModel, CategoryResponse> {

    @Override
    public CategoryResponse convert(CategoryModel source) {
        CategoryResponse target = new CategoryResponse();

        target.setId(source.getId());
        target.setCode(source.getCode());
        target.setName(source.getName());
        target.setDescription(source.getDescription());

        return target;
    }
}
