package com.riquelmemr.financetrack.security.handler;

import com.riquelmemr.financetrack.annotation.MetadataException;
import com.riquelmemr.financetrack.dto.response.ErrorResponse;
import com.riquelmemr.financetrack.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        MetadataException metadata = ex.getClass().getAnnotation(MetadataException.class);

        final int status = metadata.status().value();
        final String error = ex.getClass().getSimpleName();

        final ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now().toString(),
                status,
                error,
                ex.getMessage()
        );

        return ResponseEntity.status(metadata.status()).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(BusinessException ex) {
        final int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        final String error = ex.getClass().getSimpleName();

        final ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now().toString(),
                status,
                error,
                ex.getMessage()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }
}
