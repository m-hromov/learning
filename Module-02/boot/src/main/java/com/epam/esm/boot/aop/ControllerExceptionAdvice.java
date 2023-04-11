package com.epam.esm.boot.aop;

import com.epam.esm.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {
    @ExceptionHandler(BusinessException.class)
    public BusinessException handleBusinessException(BusinessException exception) {
        return exception;
    }

    @ExceptionHandler(Exception.class)
    public BusinessException handleServerException(BusinessException exception) {
        return new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.");
    }
}
