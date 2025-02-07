package com.example.app.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<SimpleErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(new SimpleErrorResponse(ex.getStatusCode().value(), ex.getReason()));
    }

    @Data
    @AllArgsConstructor
    static class SimpleErrorResponse {
        private int status;
        private String message;
    }
}
