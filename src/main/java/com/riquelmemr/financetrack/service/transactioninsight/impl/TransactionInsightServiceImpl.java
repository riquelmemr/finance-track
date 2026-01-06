package com.riquelmemr.financetrack.service.transactioninsight.impl;

import com.riquelmemr.financetrack.data.ExpenseByCategoryData;
import com.riquelmemr.financetrack.data.TransactionSummaryData;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.TransactionRepository;
import com.riquelmemr.financetrack.service.transactioninsight.TransactionInsightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class TransactionInsightServiceImpl implements TransactionInsightService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<ExpenseByCategoryData> findExpensesByCategory(UserModel user, LocalDate from, LocalDate to) {
        List<ExpenseByCategoryData> dataList = transactionRepository
                .findExpensesByCategory(user, from.atStartOfDay(), to.atTime(23, 59, 59));

        BigDecimal totalExpense = dataList.stream()
                .map(ExpenseByCategoryData::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dataList.forEach(data ->
                data.setPercentage(calculatePercentage(data.getTotal(), totalExpense)));

        return dataList;
    }

    @Override
    public TransactionSummaryData findSummary(UserModel user, LocalDate from, LocalDate to) {
        return transactionRepository.findSummary(user, from.atStartOfDay(), to.atTime(23, 59, 59));
    }

    private BigDecimal calculatePercentage(BigDecimal part, BigDecimal total) {
        if (isNull(part) || isNull(total) || total.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return part
                .multiply(BigDecimal.valueOf(100))
                .divide(total, 2, RoundingMode.HALF_UP);
    }
}
