package com.riquelmemr.financetrack.converter.response;

import com.riquelmemr.financetrack.data.TransactionSummaryData;
import com.riquelmemr.financetrack.dto.response.TransactionSummaryResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionSummaryResponseDataConverter
        implements Converter<TransactionSummaryData, TransactionSummaryResponse> {

    @Override
    public TransactionSummaryResponse convert(TransactionSummaryData source) {
        TransactionSummaryResponse target = new TransactionSummaryResponse();

        target.setIncome(source.getIncome());
        target.setExpense(source.getExpense());
        target.setBalance(source.getBalance());
        target.setTransactionsCount(source.getCount().intValue());

        return target;
    }
}
