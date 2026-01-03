package com.riquelmemr.financetrack.facade.transaction.impl;

import com.riquelmemr.financetrack.dto.request.CreateTransactionRequest;
import com.riquelmemr.financetrack.dto.request.TransactionFilterRequest;
import com.riquelmemr.financetrack.dto.response.TransactionPageResponse;
import com.riquelmemr.financetrack.dto.response.TransactionResponse;
import com.riquelmemr.financetrack.facade.transaction.TransactionFacade;
import com.riquelmemr.financetrack.model.TransactionModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.service.session.SessionService;
import com.riquelmemr.financetrack.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionFacadeImpl implements TransactionFacade {

    private final SessionService sessionService;
    private final TransactionService transactionService;
    private final Converter<TransactionModel, TransactionResponse> transactionResponseConverter;
    private final Converter<Page<TransactionModel>, TransactionPageResponse> transactionPageResponseConverter;

    @Override
    public TransactionResponse create(CreateTransactionRequest request) {
        UserModel user = sessionService.getCurrentUser();
        TransactionModel transaction = transactionService.create(request, user);
        return transactionResponseConverter.convert(transaction);
    }

    @Override
    public TransactionPageResponse findAll(TransactionFilterRequest filterRequest, int page, int pageSize) {
        UserModel user = sessionService.getCurrentUser();
        Page<TransactionModel> transactions = transactionService.findAll(user, filterRequest, page, pageSize);
        return transactionPageResponseConverter.convert(transactions);
    }
}
