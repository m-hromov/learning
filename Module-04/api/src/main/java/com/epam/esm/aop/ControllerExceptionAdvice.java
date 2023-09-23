package com.epam.esm.aop;

import com.epam.esm.exception.BusinessException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionAdvice {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException exception) {
        log.warn("Error:", exception);
        return ResponseEntity.status(exception.getHttpStatus()).body(mapToMessage(exception));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
        log.warn("Access has been denied", exception);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Message.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("Access denied.")
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleServerException(Exception exception) {
        log.error("Internal server error", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Message.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message("Internal server error.")
                        .build());
    }

    private Message mapToMessage(BusinessException exception) {
        return Message.builder()
                .status(exception.getHttpStatus())
                .message(exception.getMessage())
                .build();
    }

    @Data
    @Builder
    private static class Message {
        private String message;
        private HttpStatus status;
    }
}
