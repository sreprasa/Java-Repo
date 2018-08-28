package com.intv.checklist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthroizationException extends  RuntimeException {

    public AuthroizationException(String message) {
        super(message);
    }

    public AuthroizationException(String message, Throwable cause) {
        super(message, cause);
    }

}
