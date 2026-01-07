package com.riquelmemr.financetrack.converter.response;

import com.riquelmemr.financetrack.data.TransactionTimelineData;
import com.riquelmemr.financetrack.dto.response.TransactionTimelineResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionTimelineResponseDataConverter
        implements Converter<TransactionTimelineData, TransactionTimelineResponse> {

    @Override
    public TransactionTimelineResponse convert(TransactionTimelineData source) {
        TransactionTimelineResponse target = new TransactionTimelineResponse();

        target.setPeriod(source.getPeriod());
        target.setIncome(source.getIncome());
        target.setExpense(source.getExpense());

        return target;
    }
}
