package org.example.medicalconsultation.exceptions;

public class IncorrectEndingDateException extends RuntimeException {
    public IncorrectEndingDateException(String message) {
        super(message);
    }
}
