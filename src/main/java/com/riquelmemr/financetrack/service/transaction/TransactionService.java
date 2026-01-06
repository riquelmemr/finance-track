package com.riquelmemr.financetrack.service.transaction;

import com.riquelmemr.financetrack.dto.request.CreateTransactionRequest;
import com.riquelmemr.financetrack.dto.request.TransactionFilterRequest;
import com.riquelmemr.financetrack.dto.request.UpdateTransactionRequest;
import com.riquelmemr.financetrack.model.TransactionModel;
import com.riquelmemr.financetrack.model.UserModel;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface TransactionService {

    TransactionModel create(CreateTransactionRequest request, UserModel user);

    void deleteById(Long id, UserModel user);

    TransactionModel findById(Long id, UserModel user);

    TransactionModel update(Long id, UpdateTransactionRequest request, UserModel user);

    Page<TransactionModel> findAll(UserModel user, TransactionFilterRequest filterRequest, int page, int pageSize);

    BigDecimal getCurrentBalance();

}
