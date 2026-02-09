package com.riquelmemr.financetrack.exception;

import com.riquelmemr.financetrack.annotation.MetadataException;
import org.springframework.http.HttpStatus;

@MetadataException(status = HttpStatus.INTERNAL_SERVER_ERROR)
public class StrategyNotFoundException extends BusinessException {
    public StrategyNotFoundException(String message) {
        super(message);
    }
}
