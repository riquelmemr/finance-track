package com.riquelmemr.financetrack.controller;

import com.riquelmemr.financetrack.dto.request.CreateTransactionRequest;
import com.riquelmemr.financetrack.dto.request.TransactionFilterRequest;
import com.riquelmemr.financetrack.dto.request.UpdateTransactionRequest;
import com.riquelmemr.financetrack.dto.response.*;
import com.riquelmemr.financetrack.facade.transaction.TransactionFacade;
import com.riquelmemr.financetrack.facade.transactioninsight.TransactionInsightFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController extends BaseController {

    private static final String TRANSACTION_CREATED_SUCCESSFULLY_MESSAGE = "Transaction created with successfully.";
    private static final String TRANSACTION_FOUND_SUCESSFULLY_MESSAGE = "Transaction found with sucessfully.";
    private static final String TRANSACTION_UPDATED_SUCESSFULLLY_MESSAGE = "Transaction updated with sucessfully.";
    private static final String TRANSACTIONS_FOUND_SUCCESSFULLY_MESSAGE = "Transactions found with successfully.";
    private static final String SUMMARY_CALCULATED_SUCESSFULLY_MESSAGE = "Summary calculated with sucessfully.";
    private static final String EXPENSE_BY_CATEGORY_CALCULATED_SUCCESSFULLY_MESSAGE = "Expense by category calculated with successfully.";

    private final TransactionFacade transactionFacade;
    private final TransactionInsightFacade transactionInsightFacade;

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

    @Secured("ROLE_BASIC")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        transactionFacade.deleteById(id);
        return handleNoContentResponse();
    }

    @Secured("ROLE_BASIC")
    @GetMapping("/{id}")
    public ResponseEntity<DataWrapperResponse<TransactionResponse>> findById(@PathVariable Long id) {
        TransactionResponse response = transactionFacade.findById(id);
        return handleResponse(HttpStatus.OK, response, TRANSACTION_FOUND_SUCESSFULLY_MESSAGE);
    }

    @Secured("ROLE_BASIC")
    @PutMapping("/{id}")
    public ResponseEntity<DataWrapperResponse<TransactionResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTransactionRequest request
    ) {
        TransactionResponse response = transactionFacade.update(id, request);
        return handleResponse(HttpStatus.OK, response, TRANSACTION_UPDATED_SUCESSFULLLY_MESSAGE);
    }

    @Secured("ROLE_BASIC")
    @GetMapping("/insights/summary")
    public ResponseEntity<DataWrapperResponse<TransactionSummaryResponse>> findSummary(
            @RequestParam(value = "from") LocalDate from,
            @RequestParam(value = "to") LocalDate to
    ) {
        TransactionSummaryResponse response = transactionInsightFacade.findSummary(from, to);
        return handleResponse(HttpStatus.OK, response, SUMMARY_CALCULATED_SUCESSFULLY_MESSAGE);
    }

    @Secured("ROLE_BASIC")
    @GetMapping("/insights/expense-by-category")
    public ResponseEntity<DataWrapperResponse<List<ExpenseByCategoryResponse>>> findExpenseByCategory(
            @RequestParam(value = "from") LocalDate from,
            @RequestParam(value = "to") LocalDate to
    ) {
        List<ExpenseByCategoryResponse> response = transactionInsightFacade.findExpenseByCategory(from, to);
        return handleResponse(HttpStatus.OK, response, EXPENSE_BY_CATEGORY_CALCULATED_SUCCESSFULLY_MESSAGE);
    }
}
