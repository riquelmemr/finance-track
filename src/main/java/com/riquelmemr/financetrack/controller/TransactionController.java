package com.riquelmemr.financetrack.controller;

import com.riquelmemr.financetrack.dto.request.CreateTransactionRequest;
import com.riquelmemr.financetrack.dto.request.TransactionFilterRequest;
import com.riquelmemr.financetrack.dto.response.DataWrapperResponse;
import com.riquelmemr.financetrack.dto.response.TransactionPageResponse;
import com.riquelmemr.financetrack.dto.response.TransactionResponse;
import com.riquelmemr.financetrack.facade.transaction.TransactionFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController extends BaseController {

    private static final String TRANSACTION_CREATED_SUCCESSFULLY_MESSAGE = "Transaction created with successfully.";
    private static final String TRANSACTIONS_FOUND_SUCCESSFULLY_MESSAGE = "Transactions found with successfully.";

    private final TransactionFacade transactionFacade;

    @Secured("ROLE_BASIC")
    @PostMapping
    public ResponseEntity<DataWrapperResponse<TransactionResponse>> create(@Valid @RequestBody CreateTransactionRequest request) {
        TransactionResponse transactionResponse = transactionFacade.create(request);
        return handleResponse(HttpStatus.OK, transactionResponse, TRANSACTION_CREATED_SUCCESSFULLY_MESSAGE);
    }

    @Secured("ROLE_BASIC")
    @GetMapping
    public ResponseEntity<DataWrapperResponse<TransactionPageResponse>> findAll(
            TransactionFilterRequest filter,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "25") int pageSize
    ) {
        TransactionPageResponse pageResponse = transactionFacade.findAll(filter, page, pageSize);
        return handleResponse(HttpStatus.OK, pageResponse, TRANSACTIONS_FOUND_SUCCESSFULLY_MESSAGE);
    }
}
