package com.riquelmemr.financetrack.converter.data;

import com.riquelmemr.financetrack.data.TransactionTimelineByYearData;
import com.riquelmemr.financetrack.data.TransactionTimelineData;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionTimelineYearConverter
        implements Converter<TransactionTimelineByYearData, TransactionTimelineData> {

    @Override
    public TransactionTimelineData convert(TransactionTimelineByYearData source) {
        TransactionTimelineData target = new TransactionTimelineData();

        target.setPeriod(String.valueOf(source.getYear()));
        target.setIncome(source.getIncome());
        target.setExpense(source.getExpense());

        return target;
    }
}
