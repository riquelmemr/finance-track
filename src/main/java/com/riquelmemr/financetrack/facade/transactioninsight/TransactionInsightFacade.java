package com.riquelmemr.financetrack.facade.transactioninsight;

import com.riquelmemr.financetrack.dto.response.ExpenseByCategoryResponse;
import com.riquelmemr.financetrack.dto.response.TransactionSummaryResponse;

import java.time.LocalDate;
import java.util.List;

public interface TransactionInsightFacade {

    TransactionSummaryResponse findSummary(LocalDate from, LocalDate to);

    List<ExpenseByCategoryResponse> findExpenseByCategory(LocalDate from, LocalDate to);
}
