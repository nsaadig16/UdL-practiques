package org.example.services.exceptions;

public class ConnectException extends RuntimeException {
    public ConnectException(String message) {
        super(message);
    }
}
