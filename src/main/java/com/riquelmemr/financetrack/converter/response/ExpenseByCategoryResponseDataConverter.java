package com.riquelmemr.financetrack.converter.response;

import com.riquelmemr.financetrack.data.ExpenseByCategoryData;
import com.riquelmemr.financetrack.dto.response.ExpenseByCategoryResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ExpenseByCategoryResponseDataConverter implements Converter<ExpenseByCategoryData, ExpenseByCategoryResponse> {

    @Override
    public ExpenseByCategoryResponse convert(ExpenseByCategoryData source) {
        ExpenseByCategoryResponse target = new ExpenseByCategoryResponse();

        target.setCategory(source.getCategory());
        target.setTotal(source.getTotal());
        target.setPercentage(source.getPercentage());

        return target;
    }
}
