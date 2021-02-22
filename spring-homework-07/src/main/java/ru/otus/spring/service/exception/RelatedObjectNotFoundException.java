package ru.otus.spring.service.exception;

public class RelatedObjectNotFoundException extends RuntimeException {
    public RelatedObjectNotFoundException(String message) {
        super(message);
    }
}
