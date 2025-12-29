package com.riquelmemr.financetrack.exception;

import com.riquelmemr.financetrack.annotation.MetadataException;
import org.springframework.http.HttpStatus;

@MetadataException(status = HttpStatus.NOT_FOUND)
public class ModelNotFoundException extends BusinessException {

    public ModelNotFoundException(String message) {
        super(message);
    }

    public ModelNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
