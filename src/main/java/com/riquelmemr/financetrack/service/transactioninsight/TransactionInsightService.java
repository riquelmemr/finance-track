package com.riquelmemr.financetrack.service.transactioninsight;

import com.riquelmemr.financetrack.data.ExpenseByCategoryData;
import com.riquelmemr.financetrack.data.TransactionSummaryData;
import com.riquelmemr.financetrack.model.UserModel;

import java.time.LocalDate;
import java.util.List;

public interface TransactionInsightService {

    List<ExpenseByCategoryData> findExpensesByCategory(UserModel user, LocalDate from, LocalDate to);

    TransactionSummaryData findSummary(UserModel user, LocalDate from, LocalDate to);
}
