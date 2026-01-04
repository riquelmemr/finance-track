package com.riquelmemr.financetrack.converter.response;

import com.riquelmemr.financetrack.dto.response.CategoryPageResponse;
import com.riquelmemr.financetrack.dto.response.CategoryResponse;
import com.riquelmemr.financetrack.model.CategoryModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryPageResponseConverter implements Converter<Page<CategoryModel>, CategoryPageResponse> {

    private final Converter<CategoryModel, CategoryResponse> categoryResponseConverter;

    @Override
    public CategoryPageResponse convert(Page<CategoryModel> source) {
        CategoryPageResponse target = new CategoryPageResponse();

        target.setPage(source.getPageable().getPageNumber());
        target.setPageSize(source.getPageable().getPageSize());
        target.setCategories(getCategories(source));

        return target;
    }

    private List<CategoryResponse> getCategories(Page<CategoryModel> source) {
        return source.getContent().stream().map(categoryResponseConverter::convert).toList();
    }
}
