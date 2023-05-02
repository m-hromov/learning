package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BusinessException{
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
