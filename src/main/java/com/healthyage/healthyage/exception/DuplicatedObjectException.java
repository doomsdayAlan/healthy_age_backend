package com.healthyage.healthyage.exception;

public class DuplicatedObjectException extends RuntimeException {
    public DuplicatedObjectException() {
        super();
    }

    public DuplicatedObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedObjectException(String message) {
        super(message);
    }

    public DuplicatedObjectException(Throwable cause) {
        super(cause);
    }
}