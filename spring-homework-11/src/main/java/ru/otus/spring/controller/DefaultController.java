package ru.otus.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice

public class DefaultController {

    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_STACK = "errorStack";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorBody(e));
    }

    private Map<String, String> createErrorBody(Exception e) {
        Map<String, String> body = new HashMap<>();
        body.put(ERROR_MESSAGE, e.getMessage());
        body.put(ERROR_STACK, e.getStackTrace().toString());
        return body;
    }
}
