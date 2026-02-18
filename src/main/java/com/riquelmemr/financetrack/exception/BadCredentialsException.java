package com.riquelmemr.financetrack.exception;

import com.riquelmemr.financetrack.annotation.MetadataException;
import org.springframework.http.HttpStatus;

@MetadataException(status = HttpStatus.UNAUTHORIZED)
public class BadCredentialsException extends BusinessException {
    public BadCredentialsException(String message) {
        super(message);
    }
}
