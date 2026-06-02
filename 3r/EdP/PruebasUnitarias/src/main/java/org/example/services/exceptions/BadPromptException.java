package org.example.services.exceptions;

public class BadPromptException extends RuntimeException {
    public BadPromptException(String message) {
        super(message);
    }
}
