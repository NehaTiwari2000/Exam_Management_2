package org.exam.exception;

public class ResourceNotFoundException extends IllegalStateException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
