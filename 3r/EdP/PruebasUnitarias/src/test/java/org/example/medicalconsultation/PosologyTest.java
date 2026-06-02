package org.example.medicalconsultation;

import org.example.medicalconsultation.exceptions.InvalidPosologyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PosologyTest {
    @Test
    @DisplayName("A valid posology is created correctly")
    void validPosology() {
        FqUnit unit = FqUnit.DAY;
        Posology pos = new Posology(10.0f, 2.0f, unit);

        assertEquals(10.0f, pos.getDose());
        assertEquals(2.0f, pos.getFreq());
        assertEquals(unit, pos.getFreqUnit());
    }

    @Test
    @DisplayName("Invalid dose throws InvalidPosologyException")
    void testInvalidDoseThrowsException() {
        FqUnit unit = FqUnit.DAY;
        Throwable exception = assertThrows(InvalidPosologyException.class, () -> {
            new Posology(0f, 2f, unit);
        });
        assertEquals("Dose cannot be 0 or less", exception.getMessage());
    }

    @Test
    @DisplayName("Invalid frequency throws InvalidPosologyException")
    void testInvalidFreqThrowsException() {
        FqUnit unit = FqUnit.DAY;
        Throwable exception = assertThrows(InvalidPosologyException.class, () -> {
            new Posology(2f, 0f, unit);
        });
        assertEquals("Frequency cannot be 0 or less", exception.getMessage());
    }

    @Test
    @DisplayName("Invalid frequency unit throws InvalidPosologyException")
    void testInvalidFreqUnitThrowsException() {
        Throwable exception = assertThrows(InvalidPosologyException.class, () -> {
            new Posology(2f, 3f, null);
        });
        assertEquals("A frequency unit is needed", exception.getMessage());
    }

    @Test
    @DisplayName("Setting a new incorrect dose throws InvalidPosologyException")
    void testInvalidNewDoseThrowsException() {
        Posology pos = new Posology(2f, 3f, FqUnit.DAY);
        Throwable exception = assertThrows(InvalidPosologyException.class, () -> {
            pos.setDose(0f);
        });
        assertEquals("Dose cannot be 0 or less", exception.getMessage());
    }

    @Test
    @DisplayName("Setting a new incorrect frequency throws InvalidPosologyException")
    void testInvalidNewFreqThrowsException() {
        Posology pos = new Posology(2f, 3f, FqUnit.DAY);
        Throwable exception = assertThrows(InvalidPosologyException.class, () -> {
            pos.setFreq(0f);
        });
        assertEquals("Frequency cannot be 0 or less", exception.getMessage());
    }

    @Test
    @DisplayName("Setting a new incorrect frequency unit throws InvalidPosologyException")
    void testInvalidNewFreqUnitThrowsException() {
        Posology pos = new Posology(2f, 3f, FqUnit.DAY);
        Throwable exception = assertThrows(InvalidPosologyException.class, () -> {
            pos.setFreqUnit(null);
        });
        assertEquals("A frequency unit is needed", exception.getMessage());
    }
}


