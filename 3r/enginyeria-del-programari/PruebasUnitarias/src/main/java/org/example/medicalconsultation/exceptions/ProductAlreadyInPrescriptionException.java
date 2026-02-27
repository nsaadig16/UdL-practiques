package org.example.medicalconsultation.exceptions;

public class ProductAlreadyInPrescriptionException extends Exception {
    public ProductAlreadyInPrescriptionException(String message) {
        super(message);
    }
}