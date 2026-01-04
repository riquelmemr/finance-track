package com.riquelmemr.financetrack.controller;

import com.riquelmemr.financetrack.dto.response.DataWrapperResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    protected <R> ResponseEntity<DataWrapperResponse<R>> handleResponse(HttpStatus status, R body, String message) {
        DataWrapperResponse<R> data = DataWrapperResponse.<R>builder()
                .withData(body)
                .withMessage(message)
                .build();

        return ResponseEntity.status(status).body(data);
    }

    protected <R> ResponseEntity<DataWrapperResponse<R>> handleResponse(HttpStatus status, R body) {
        DataWrapperResponse<R> data = DataWrapperResponse.<R>builder()
                .withData(body)
                .build();

        return ResponseEntity.status(status).body(data);
    }

    protected ResponseEntity<Void> handleNoContentResponse() {
        return ResponseEntity.noContent().build();
    }
}
