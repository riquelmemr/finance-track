package com.riquelmemr.financetrack.service.transactioninsight.impl;

import com.riquelmemr.financetrack.data.*;
import com.riquelmemr.financetrack.enums.TimelineGroupBy;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.TransactionRepository;
import com.riquelmemr.financetrack.service.transactioninsight.TransactionInsightService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
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
    private final Converter<TransactionTimelineByMonthData, TransactionTimelineData> transactionTimelineMonthConverter;
    private final Converter<TransactionTimelineByYearData, TransactionTimelineData> transactionTimelineYearConverter;

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

    @Override
    public List<TransactionTimelineData> findTimeline(UserModel user, TimelineGroupBy groupBy, LocalDate from, LocalDate to) {
        return switch (groupBy) {
            case YEAR -> findTimelineByYear(user, from, to);
            case MONTH -> findTimelineByMonth(user, from, to);
        };
    }

    private List<TransactionTimelineData> findTimelineByMonth(UserModel user, LocalDate from, LocalDate to) {
        return transactionRepository
                .findTimelineByMonth(user, from.atStartOfDay(), to.atTime(23, 59, 59))
                .stream()
                .map(transactionTimelineMonthConverter::convert)
                .toList();
    }

    private List<TransactionTimelineData> findTimelineByYear(UserModel user, LocalDate from, LocalDate to) {
        return transactionRepository
                .findTimelineByYear(user, from.atStartOfDay(), to.atTime(23, 59, 59))
                .stream()
                .map(transactionTimelineYearConverter::convert)
                .toList();
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
