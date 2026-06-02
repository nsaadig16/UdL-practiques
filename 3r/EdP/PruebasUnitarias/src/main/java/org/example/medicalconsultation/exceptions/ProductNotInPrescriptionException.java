package org.example.medicalconsultation.exceptions;

public class ProductNotInPrescriptionException extends Exception {
    public ProductNotInPrescriptionException(String message) {
        super(message);
    }
}