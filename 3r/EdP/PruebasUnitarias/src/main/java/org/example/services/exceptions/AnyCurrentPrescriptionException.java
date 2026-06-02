package org.example.services.exceptions;

public class AnyCurrentPrescriptionException extends RuntimeException {
    public AnyCurrentPrescriptionException(String message) {
        super(message);
    }
}
