package org.example.medicalconsultation;

import org.example.medicalconsultation.exceptions.InvalidGuidelineException;

import java.util.Objects;

/**
 * Represents the taking guidelines for a medicine
 */
public class TakingGuideline { // Represents the taking guidelines for a medicine
    private dayMoment dMoment;
    private float duration;
    private final Posology posology;
    private String instructions;

    /**
     * Constructor of the TakingGuideline class
     *
     * @param dM day moment to take the medicine
     * @param du duration of the treatment
     * @param d  dose of the medicine
     * @param f  frequency of the medicine
     * @param fu frequency unit of the medicine
     * @param i  additional instructions
     * @throws InvalidGuidelineException when any parameter is incorrect
     */
    public TakingGuideline(dayMoment dM, float du, float d, float f, FqUnit fu, String i) {
        checkGuideline(dM, du, i);

        this.dMoment = dM;
        this.duration = du;
        this.posology = new Posology(d, f, fu);
        this.instructions = i;
    }

    public dayMoment getdMoment() {
        return dMoment;
    }

    public float getDuration() {
        return duration;
    }

    public Posology getPosology() {
        return posology;
    }

    public float getDose() {
        return posology.getDose();
    }

    public float getFreq() {
        return posology.getFreq();
    }

    public FqUnit getFreqUnit() {
        return posology.getFreqUnit();
    }

    public String getInstructions() {
        return instructions;
    }

    public void setFreq(float freq) {
        posology.setFreq(freq);
    }

    public void setFreqUnit(FqUnit freqUnit) {
        posology.setFreqUnit(freqUnit);
    }

    public void setInstructions(String newInstructions) {
        checkGuideline(dMoment, duration, newInstructions);
        this.instructions = newInstructions;
    }

    public void setdMoment(dayMoment newDMoment) {
        checkGuideline(newDMoment, duration, instructions);
        this.dMoment = newDMoment;
    }

    public void setDuration(float newDuration) {
        checkGuideline(dMoment, newDuration, instructions);
        this.duration = newDuration;

    }

    public void setDose(float dose) {
        posology.setDose(dose);
    }

    private void checkGuideline(dayMoment dM, float du, String i) {
        if (dM == null) {
            throw new InvalidGuidelineException("Day moment cannot be null");
        }
        if (du <= 0) {
            throw new InvalidGuidelineException("Duration cannot be 0 or less");
        }
        if (i == null || i.isBlank()) {
            throw new InvalidGuidelineException("Instructions cannot be empty");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TakingGuideline that = (TakingGuideline) o;
        return Float.compare(that.duration, duration) == 0 &&
                dMoment == that.dMoment &&
                Objects.equals(posology, that.posology) &&
                Objects.equals(instructions, that.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dMoment, duration, posology, instructions);
    }
}



