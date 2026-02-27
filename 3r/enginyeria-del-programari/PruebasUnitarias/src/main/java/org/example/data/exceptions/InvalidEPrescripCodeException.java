package org.example.data.exceptions;

public class InvalidEPrescripCodeException extends RuntimeException {
    public InvalidEPrescripCodeException(String message) {
        super(message);
    }
}