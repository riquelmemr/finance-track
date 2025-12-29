package com.riquelmemr.financetrack.converter.response;

import com.riquelmemr.financetrack.dto.response.CreateCategoryResponse;
import com.riquelmemr.financetrack.model.CategoryModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateCategoryResponseConverter implements Converter<CategoryModel, CreateCategoryResponse> {

    @Override
    public CreateCategoryResponse convert(CategoryModel source) {
        CreateCategoryResponse target = new CreateCategoryResponse();

        target.setCode(source.getCode());
        target.setName(source.getName());
        target.setDescription(source.getDescription());

        return target;
    }
}
