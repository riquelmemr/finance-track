package com.riquelmemr.financetrack.exception;

import com.riquelmemr.financetrack.annotation.MetadataException;
import org.springframework.http.HttpStatus;

@MetadataException(status = HttpStatus.BAD_REQUEST)
public class AuthenticationException extends BusinessException {
    public AuthenticationException(String message) {
        super(message);
    }
}
