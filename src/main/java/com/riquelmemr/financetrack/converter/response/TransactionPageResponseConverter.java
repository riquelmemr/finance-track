package com.riquelmemr.financetrack.converter.response;

import com.riquelmemr.financetrack.dto.response.TransactionPageResponse;
import com.riquelmemr.financetrack.dto.response.TransactionResponse;
import com.riquelmemr.financetrack.model.TransactionModel;
import com.riquelmemr.financetrack.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionPageResponseConverter implements Converter<Page<TransactionModel>, TransactionPageResponse> {

    private final Converter<TransactionModel, TransactionResponse> transactionResponseConverter;
    private final TransactionService transactionService;

    @Override
    public TransactionPageResponse convert(Page<TransactionModel> source) {
        TransactionPageResponse target = new TransactionPageResponse();

        target.setBalance(transactionService.getCurrentBalance());
        target.setPage(source.getPageable().getPageNumber());
        target.setPageSize(source.getPageable().getPageSize());
        target.setTransactions(getTransactions(source));

        return target;
    }

    private List<TransactionResponse> getTransactions(Page<TransactionModel> source) {
        return source.getContent().stream().map(transactionResponseConverter::convert).toList();
    }
}
