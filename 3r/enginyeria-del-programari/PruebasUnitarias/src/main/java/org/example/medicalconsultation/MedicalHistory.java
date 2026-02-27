package org.example.medicalconsultation;

import org.example.data.HealthCardID;
import org.example.medicalconsultation.exceptions.IncorrectParametersException;

/**
 * A class that represents a medical history
 */
public class MedicalHistory {
    private HealthCardID cip;
    private int membShipNumb;
    private String history;

    /**
     * Constructor of the MedicalHistory class
     *
     * @param cip           HealthCardID of the patient
     * @param memberShipNum Membership number of the family doctor
     * @throws IncorrectParametersException when any parameter is incorrect
     */
    public MedicalHistory(HealthCardID cip, int memberShipNum) throws IncorrectParametersException {
        if (cip == null) {
            throw new IncorrectParametersException("The CIP cannot be null");
        }
        if (memberShipNum < 0) {
            throw new IncorrectParametersException("The membership number cannot be negative");
        }
        this.cip = cip;
        this.membShipNumb = memberShipNum;
        this.history = "";
    }

    /**
     * Adds new annotations to the patient history
     *
     * @param annot String containing the new annotation
     * @throws IncorrectParametersException when the annotation is null or blank
     */
    public void addMedicalHistoryAnnotations(String annot) throws IncorrectParametersException {
        if (annot == null || annot.isBlank()) {
            throw new IncorrectParametersException("An annotation cannot be null");
        }
        this.history += annot;
    }

    /**
     * Modifies the family doctor for the patient
     *
     * @param mshN new membership number of the family doctor
     * @throws IncorrectParametersException when the membership number is negative
     */
    public void setNewDoctor(int mshN) throws IncorrectParametersException {
        if (mshN < 0) {
            throw new IncorrectParametersException("The membership number cannot be negative");
        }
        this.membShipNumb = mshN;
    }

    public HealthCardID getCip() {
        return cip;
    }

    public int getMembShipNumb() {
        return membShipNumb;
    }

    public String getHistory() {
        return history;
    }
}
