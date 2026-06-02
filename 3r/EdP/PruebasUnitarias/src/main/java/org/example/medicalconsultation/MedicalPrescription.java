package org.example.medicalconsultation;

import org.example.data.DigitalSignature;
import org.example.data.HealthCardID;
import org.example.data.ProductID;
import org.example.data.ePrescripCode;
import org.example.data.exceptions.InvalidProductIDException;
import org.example.medicalconsultation.exceptions.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a medical prescription
 */
public class MedicalPrescription {
    private final HealthCardID cip;
    private final int membShipNumb;
    private final String illness;
    private ePrescripCode prescCode;
    private Date prescDate;
    private Date endDate;
    private DigitalSignature eSign;

    private final Map<ProductID, TakingGuideline> prescriptionLines;

    /**
     * Constructor of MedicalPrescription
     *
     * @param cip          HealthCardID of the patient
     * @param membShipNumb Membership number of the medical professional
     * @param illness      Illness description
     * @throws IncorrectParametersException when any parameter is invalid
     */
    public MedicalPrescription(HealthCardID cip, int membShipNumb, String illness) {
        if (cip == null) {
            throw new IncorrectParametersException("HealthCardID cannot be null");
        }
        if (membShipNumb < 0) {
            throw new IncorrectParametersException("Membership number should be positive");
        }
        if (illness == null || illness.isEmpty()) {
            throw new IncorrectParametersException("Illness cannot be null or empty");
        }
        this.cip = cip;
        this.membShipNumb = membShipNumb;
        this.illness = illness;
        this.prescriptionLines = new HashMap<>();
    }

    /**
     * Adds a line to the prescription
     *
     * @param prodID  the product ID
     * @param instruc array of strings representing taking guidelines:
     *                [dayMoment, duration, dose, freq, freqUnit, instructions]
     * @throws ProductAlreadyInPrescriptionException when the product is already in the prescription
     * @throws IncorrectTakingGuidelinesException    when the taking guidelines are incorrect
     */
    public void addLine(ProductID prodID, String[] instruc)
            throws ProductAlreadyInPrescriptionException, IncorrectTakingGuidelinesException {

        if (prescriptionLines.containsKey(prodID)) {
            throw new ProductAlreadyInPrescriptionException(
                    "Product " + prodID.getProductID() + " is already in the prescription"
            );
        }

        if (instruc == null || instruc.length < 6) {
            throw new IncorrectTakingGuidelinesException(
                    "Instructions array must contain at least 6 elements: " +
                            "[dayMoment, duration, dose, freq, freqUnit, instructions]"
            );
        }

        try {
            // Parse instructions: [dayMoment, duration, dose, freq, freqUnit, instructions]
            dayMoment dM = dayMoment.valueOf(instruc[0].toUpperCase());
            float duration = Float.parseFloat(instruc[1]);
            float dose = Float.parseFloat(instruc[2]);
            float freq = Float.parseFloat(instruc[3]);
            FqUnit freqUnit = FqUnit.valueOf(instruc[4].toUpperCase());
            String instructions = instruc[5];

            if (duration <= 0 || dose <= 0 || freq <= 0) {
                throw new IncorrectTakingGuidelinesException(
                        "Duration, dose, and frequency must be positive values"
                );
            }


            TakingGuideline guideline = new TakingGuideline(dM, duration, dose, freq, freqUnit, instructions);
            prescriptionLines.put(prodID, guideline);

        } catch (InvalidGuidelineException | InvalidPosologyException e) {
            throw new IncorrectTakingGuidelinesException(
                    "Error creating guideline: " + e.getMessage()
            );
        } catch (NumberFormatException e) {
            throw new IncorrectTakingGuidelinesException(
                    "Invalid numeric values in taking guidelines"
            );
        } catch (IllegalArgumentException e) {
            throw new IncorrectTakingGuidelinesException(
                    "Invalid format in taking guidelines: " + e.getMessage()
            );
        }
    }

    /**
     * Modifies the dose of a product in the prescription
     *
     * @param prodID  the product ID
     * @param newDose the new dose value
     * @throws ProductNotInPrescriptionException  when the product is not in the prescription
     * @throws IncorrectTakingGuidelinesException when the new dose is invalid
     */
    public void modifyDoseInLine(ProductID prodID, float newDose)
            throws ProductNotInPrescriptionException, IncorrectTakingGuidelinesException {

        if (prodID == null) {
            throw new InvalidProductIDException("ProductID cannot be null");
        }
        if (newDose <= 0) {
            throw new IncorrectTakingGuidelinesException("Dose must be a positive value");
        }
        TakingGuideline guideline = prescriptionLines.get(prodID);

        if (guideline == null) {
            throw new ProductNotInPrescriptionException(
                    "Product " + prodID.getProductID() + " is not in the prescription"
            );
        }


        guideline.getPosology().setDose(newDose);
    }

    /**
     * Removes a line from the prescription
     *
     * @param prodID the product ID
     * @throws ProductNotInPrescriptionException when the product is not in the prescription
     */
    public void removeLine(ProductID prodID) throws ProductNotInPrescriptionException, InvalidProductIDException {
        if (prodID == null) {
            throw new InvalidProductIDException("ProductID cannot be null");
        }
        if (!prescriptionLines.containsKey(prodID)) {
            throw new ProductNotInPrescriptionException(
                    "Product " + prodID.getProductID() + " is not in the prescription"
            );
        }

        prescriptionLines.remove(prodID);
    }

    /**
     * Checks if the prescription is complete (has signature and dates)
     */
    public boolean isComplete() {
        return eSign != null && isDateSet();
    }

    /**
     * Checks if the prescription dates are set
     */
    public boolean isDateSet() {
        return prescDate != null && endDate != null;
    }

    /**
     * Gets the number of prescription lines
     */
    public int getLineCount() {
        return prescriptionLines.size();
    }

    // Setters
    public void setPrescCode(ePrescripCode prescCode) {
        if (prescCode == null) {
            throw new IncorrectParametersException("Prescription code cannot be null");
        }
        this.prescCode = prescCode;
    }

    void setPrescDate(Date prescDate) {
        if (prescDate == null) {
            throw new IncorrectParametersException("Prescription date cannot be null");
        }
        this.prescDate = prescDate;
    }

    void setEndDate(Date endDate) {
        if (endDate == null) {
            throw new IncorrectParametersException("End date cannot be null");
        }
        this.endDate = endDate;
    }

    void seteSign(DigitalSignature eSign) {
        if (eSign == null) {
            throw new IncorrectParametersException("Digital signature cannot be null");
        }
        this.eSign = eSign;
    }

    // Getters
    public HealthCardID getCip() {
        return cip;
    }

    public int getMembShipNumb() {
        return membShipNumb;
    }

    public String getIllness() {
        return illness;
    }

    public ePrescripCode getPrescCode() {
        return prescCode;
    }


    public Date getPrescDate() {
        return prescDate;
    }


    public Date getEndDate() {
        return endDate;
    }


    public DigitalSignature geteSign() {
        return eSign;
    }


    public Map<ProductID, TakingGuideline> getPrescriptionLines() {
        return prescriptionLines;
    }

    @Override
    public String toString() {
        return "MedicalPrescription{" +
                "cip=" + cip +
                ", membShipNumb=" + membShipNumb +
                ", illness='" + illness + '\'' +
                ", prescCode=" + prescCode +
                ", prescDate=" + prescDate +
                ", endDate=" + endDate +
                ", lines=" + prescriptionLines.size() +
                '}';
    }
}