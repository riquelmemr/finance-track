package com.riquelmemr.financetrack.specification.impl;

import com.riquelmemr.financetrack.dto.request.TransactionFilterRequest;
import com.riquelmemr.financetrack.model.TransactionModel;
import com.riquelmemr.financetrack.specification.AbstractSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class TransactionSpecification extends AbstractSpecification<TransactionModel, TransactionFilterRequest> {

    @Override
    public Specification<TransactionModel> toSpecification(TransactionFilterRequest data) {
        return Specification
                .where(equal("user", "id", data.getUserId()))
                .and(between("date", getStartDate(data.getStartDate()), getEndDate(data.getEndDate())))
                .and(equal("type", data.getType()))
                .and(between("amount", data.getMinAmount(), data.getMaxAmount()));
    }

    private LocalDateTime getStartDate(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay() : null;
    }

    private LocalDateTime getEndDate(LocalDate localDate) {
        return localDate != null ? localDate.atTime(23, 59, 59) : null;
    }
}
