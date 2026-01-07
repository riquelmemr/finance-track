package com.riquelmemr.financetrack.converter.data;

import com.riquelmemr.financetrack.data.TransactionTimelineByMonthData;
import com.riquelmemr.financetrack.data.TransactionTimelineData;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionTimelineMonthConverter
        implements Converter<TransactionTimelineByMonthData, TransactionTimelineData> {

    @Override
    public TransactionTimelineData convert(TransactionTimelineByMonthData source) {
        TransactionTimelineData target = new TransactionTimelineData();

        target.setPeriod(getPeriod(source.getYear(), source.getMonth()));
        target.setIncome(source.getIncome());
        target.setExpense(source.getExpense());

        return target;
    }

    private String getPeriod(Integer year, Integer month) {
        return String.format("%d-%02d", year, month);
    }
}
