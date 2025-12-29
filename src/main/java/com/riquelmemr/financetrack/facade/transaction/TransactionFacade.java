package com.riquelmemr.financetrack.facade.transaction;

import com.riquelmemr.financetrack.dto.request.CreateTransactionRequest;
import com.riquelmemr.financetrack.dto.response.TransactionPageResponse;
import com.riquelmemr.financetrack.dto.response.TransactionResponse;

public interface TransactionFacade {

    TransactionResponse create(CreateTransactionRequest request);

    TransactionPageResponse findAll(int page, int pageSize);
}
