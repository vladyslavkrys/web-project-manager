package com.spring.manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailHasDuplicateException extends RuntimeException {
    public EmailHasDuplicateException(String message) {
        super(message);
    }
}
