package org.example.services.exceptions;

public class NotCompletedMedicalPrescription extends RuntimeException {
    public NotCompletedMedicalPrescription(String message) {
        super(message);
    }
}
