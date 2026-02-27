package org.example.data.exceptions;

public class InvalidProductIDException extends RuntimeException {
    public InvalidProductIDException(String message) {
        super(message);
    }
}