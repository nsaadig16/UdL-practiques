package org.example.medicalconsultation;


import org.example.medicalconsultation.exceptions.InvalidPosologyException;

import java.util.Objects;

/**
 * Represents the posology of a medicine
 */
public class Posology { // A class that represents the posology of a medicine
    private float dose;
    private float freq;
    private FqUnit freqUnit;

    /**
     * Constructor of the Posology class
     *
     * @param d dose of the medicine
     * @param f frequency of the medicine
     * @param u frequency unit of the medicine
     * @throws InvalidPosologyException when any parameter is incorrect
     */
    public Posology(float d, float f, FqUnit u) {
        checkPosology(d, f, u);

        this.dose = d;
        this.freq = f;
        this.freqUnit = u;
    }

    // Setters
    public void setDose(float newDose) {
        checkPosology(newDose, freq, freqUnit);
        this.dose = newDose;
    }

    public void setFreq(float newFreq) {
        checkPosology(dose, newFreq, freqUnit);
        this.freq = newFreq;
    }

    public void setFreqUnit(FqUnit newFreqUnit) {
        checkPosology(dose, freq, newFreqUnit);
        this.freqUnit = newFreqUnit;
    }

    // Getters
    public float getDose() {
        return dose;
    }

    public float getFreq() {
        return freq;
    }

    public FqUnit getFreqUnit() {
        return freqUnit;
    }

    private void checkPosology(float d, float f, FqUnit fu) {
        if (d <= 0) {
            throw new InvalidPosologyException("Dose cannot be 0 or less");
        }
        if (f <= 0) {
            throw new InvalidPosologyException("Frequency cannot be 0 or less");
        }
        if (fu == null) {
            throw new InvalidPosologyException("A frequency unit is needed");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Posology pos = (Posology) obj;
        return Float.compare(dose, pos.dose) == 0 &&
                Float.compare(freq, pos.freq) == 0 &&
                freqUnit == pos.freqUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dose, freq, freqUnit);
    }
}

