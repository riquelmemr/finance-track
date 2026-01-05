package com.riquelmemr.financetrack.facade.transaction;

import com.riquelmemr.financetrack.dto.request.CreateTransactionRequest;
import com.riquelmemr.financetrack.dto.request.TransactionFilterRequest;
import com.riquelmemr.financetrack.dto.request.UpdateTransactionRequest;
import com.riquelmemr.financetrack.dto.response.TransactionPageResponse;
import com.riquelmemr.financetrack.dto.response.TransactionResponse;
import com.riquelmemr.financetrack.dto.response.TransactionSummaryResponse;

import java.time.LocalDate;

public interface TransactionFacade {

    TransactionResponse create(CreateTransactionRequest request);

    TransactionPageResponse findAll(TransactionFilterRequest filterRequest, int page, int pageSize);

    void deleteById(Long id);

    TransactionResponse update(Long id, UpdateTransactionRequest request);

    TransactionResponse findById(Long id);

    TransactionSummaryResponse getSummary(LocalDate from, LocalDate to);
}
