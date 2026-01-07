package com.riquelmemr.financetrack.facade.transactioninsight.impl;

import com.riquelmemr.financetrack.data.ExpenseByCategoryData;
import com.riquelmemr.financetrack.data.TransactionSummaryData;
import com.riquelmemr.financetrack.data.TransactionTimelineData;
import com.riquelmemr.financetrack.dto.response.ExpenseByCategoryResponse;
import com.riquelmemr.financetrack.dto.response.TransactionSummaryResponse;
import com.riquelmemr.financetrack.dto.response.TransactionTimelineResponse;
import com.riquelmemr.financetrack.enums.TimelineGroupBy;
import com.riquelmemr.financetrack.facade.transactioninsight.TransactionInsightFacade;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.service.session.SessionService;
import com.riquelmemr.financetrack.service.transactioninsight.TransactionInsightService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionInsightFacadeImpl implements TransactionInsightFacade {

    private final SessionService sessionService;
    private final TransactionInsightService transactionInsightService;
    private final Converter<TransactionSummaryData, TransactionSummaryResponse> transactionSummaryResponseDataConverter;
    private final Converter<ExpenseByCategoryData, ExpenseByCategoryResponse> expenseByCategoryResponseDataConverter;
    private final Converter<TransactionTimelineData, TransactionTimelineResponse> transactionTimelineResponseDataConverter;

    @Override
    public TransactionSummaryResponse findSummary(LocalDate from, LocalDate to) {
        UserModel user = sessionService.getCurrentUser();
        TransactionSummaryData data = transactionInsightService.findSummary(user, from, to);
        return transactionSummaryResponseDataConverter.convert(data);
    }

    @Override
    public List<ExpenseByCategoryResponse> findExpenseByCategory(LocalDate from, LocalDate to) {
        UserModel user = sessionService.getCurrentUser();
        List<ExpenseByCategoryData> dataList = transactionInsightService.findExpensesByCategory(user, from, to);
        return dataList.stream().map(expenseByCategoryResponseDataConverter::convert).toList();
    }

    @Override
    public List<TransactionTimelineResponse> findTimeline(TimelineGroupBy groupBy, LocalDate from, LocalDate to) {
        UserModel user = sessionService.getCurrentUser();
        List<TransactionTimelineData> dataList = transactionInsightService.findTimeline(user, groupBy, from, to);
        return dataList.stream().map(transactionTimelineResponseDataConverter::convert).toList();
    }
}
