package com.riquelmemr.financetrack.converter.response;

import com.riquelmemr.financetrack.dto.response.TransactionResponse;
import com.riquelmemr.financetrack.model.TransactionModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.riquelmemr.financetrack.utils.DateUtils.toBrazilDateFormat;

@Component
public class TransactionResponseConverter implements Converter<TransactionModel, TransactionResponse> {

    @Override
    public TransactionResponse convert(TransactionModel source) {
        TransactionResponse target = new TransactionResponse();

        target.setId(source.getId());
        target.setAmount(source.getAmount());
        target.setType(source.getType());
        target.setCategory(source.getCategory().getCode());
        target.setDescription(source.getDescription());
        target.setDate(toBrazilDateFormat(source.getDate()));

        return target;
    }
}
