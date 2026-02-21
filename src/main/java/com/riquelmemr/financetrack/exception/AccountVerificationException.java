package com.riquelmemr.financetrack.exception;

import com.riquelmemr.financetrack.annotation.MetadataException;
import org.springframework.http.HttpStatus;

@MetadataException(status = HttpStatus.UNAUTHORIZED)
public class AccountVerificationException extends BusinessException {
    public AccountVerificationException(String message) {
        super(message);
    }
}
