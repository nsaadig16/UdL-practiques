package org.example.data;

import org.example.data.exceptions.InvalidEPrescripCodeException;

/**
 * ePrescripCode class represents the unique code associated with an electronic prescription.
 * It ensures that the code is valid according to specified rules.
 */
public class ePrescripCode {
    private final String ePrescripCode;

    /**
     * Constructor of ePrescripCode
     *
     * @param prescrip String representing the e-prescription code
     * @throws InvalidEPrescripCodeException when the code is null, empty, or not 20 characters long
     */
    public ePrescripCode(String prescrip) throws InvalidEPrescripCodeException {
        if (prescrip == null) {
            throw new InvalidEPrescripCodeException("ePrescripCode cannot be null");
        }
        if (prescrip.isBlank()) {
            throw new InvalidEPrescripCodeException("ePrescripCode cannot be empty");
        }
        if (prescrip.length() != 20) {
            throw new InvalidEPrescripCodeException("ePrescripCode should be 20 characters");
        }
        if (!prescrip.matches("^[a-zA-Z0-9]+$")) {
            throw new InvalidEPrescripCodeException("ePrescripCode should be alphanumerical");
        }
        this.ePrescripCode = prescrip;
    }

    public String getePrescripCode() {
        return ePrescripCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ePrescripCode prescripCode = (ePrescripCode) o;
        return ePrescripCode.equals(prescripCode.ePrescripCode);
    }

    @Override
    public int hashCode() {
        return ePrescripCode.hashCode();
    }

    @Override
    public String toString() {
        return "ePrescripCode {" + "e-prescript code = '" + ePrescripCode + '\'' + '}';
    }
}
