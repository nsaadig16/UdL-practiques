package org.example.data.exceptions;

public class InvalidPersonalIDException extends RuntimeException {
    public InvalidPersonalIDException(String message) {
        super(message);
    }
}
