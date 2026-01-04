package com.riquelmemr.financetrack.service.transaction.impl;

import com.riquelmemr.financetrack.dto.request.CreateTransactionRequest;
import com.riquelmemr.financetrack.dto.request.TransactionFilterRequest;
import com.riquelmemr.financetrack.enums.TransactionType;
import com.riquelmemr.financetrack.exception.ModelNotFoundException;
import com.riquelmemr.financetrack.model.CategoryModel;
import com.riquelmemr.financetrack.model.TransactionModel;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.repository.TransactionRepository;
import com.riquelmemr.financetrack.service.category.CategoryService;
import com.riquelmemr.financetrack.service.session.SessionService;
import com.riquelmemr.financetrack.service.transaction.TransactionService;
import com.riquelmemr.financetrack.specification.impl.TransactionSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private static final String CREATION_TIME_ATTRIBUTE = "creationTime";

    private final TransactionRepository transactionRepository;
    private final TransactionSpecification transactionSpecification;
    private final CategoryService categoryService;
    private final SessionService sessionService;

    @Override
    @Transactional
    public TransactionModel create(CreateTransactionRequest request, UserModel user) {
        CategoryModel category = categoryService.findByCodeAndUser(request.getCategoryCode(), user)
                .orElseThrow(() ->
                        new ModelNotFoundException("Category with code " + request.getCategoryCode() + " not found.")
                );

        TransactionModel transaction = TransactionModel.builder()
                .withAmount(BigDecimal.valueOf(request.getAmount()))
                .withUser(user)
                .withCategory(category)
                .withDescription(request.getDescription())
                .withType((TransactionType.valueOf(request.getType())))
                .withDate(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }

    @Override
    public Page<TransactionModel> findAll(UserModel user, TransactionFilterRequest filterRequest, int page, int pageSize) {
        filterRequest.setUserId(user.getId());

        Specification<TransactionModel> spec = transactionSpecification.toSpecification(filterRequest);
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.Direction.DESC, CREATION_TIME_ATTRIBUTE);

        return transactionRepository.findAll(spec, pageRequest);
    }

    @Override
    public BigDecimal calculateBalance() {
        UserModel user = sessionService.getCurrentUser();
        return transactionRepository.calculateBalance(user.getId());
    }
}
