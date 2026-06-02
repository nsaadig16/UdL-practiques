package org.example.medicalconsultation;

import org.example.medicalconsultation.exceptions.InvalidGuidelineException;
import org.example.medicalconsultation.exceptions.InvalidPosologyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TakingGuidelineTest {
    @Test
    @DisplayName("A valid guideline is created correctly")
    void validGuideline() {
        TakingGuideline guide = new TakingGuideline(dayMoment.AFTERBREAKFAST, 1.0f, 2.0f, 3.0f, FqUnit.DAY, "Tomar despues de desayunar");

        assertEquals(dayMoment.AFTERBREAKFAST, guide.getdMoment());
        assertEquals(1.0f, guide.getDuration());
        assertEquals(new Posology(2.0f, 3.0f, FqUnit.DAY), guide.getPosology());
        assertEquals(2.0f, guide.getDose());
        assertEquals(3.0f, guide.getFreq());
        assertEquals(FqUnit.DAY, guide.getFreqUnit());
        assertEquals("Tomar despues de desayunar", guide.getInstructions());
    }

    @Test
    @DisplayName("Invalid day moment throws InvalidGuidelineException")
    void invalidDayMomentThrowsException() {
        Throwable exception = assertThrows(InvalidGuidelineException.class, () -> {
            new TakingGuideline(null, 1.0f, 2.0f, 3.0f, FqUnit.DAY, "Tomar despues de desayunar");
        });
        assertEquals("Day moment cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Invalid duration throws InvalidGuidelineException")
    void invalidDurationThrowsException() {
        Throwable exception = assertThrows(InvalidGuidelineException.class, () -> {
            new TakingGuideline(dayMoment.AFTERBREAKFAST, 0.0f, 2.0f, 3.0f, FqUnit.DAY, "Tomar despues de desayunar");
        });
        assertEquals("Duration cannot be 0 or less", exception.getMessage());
    }

    @Test
    @DisplayName("Empty instructions throw InvalidGuidelineException")
    void emptyInstructionsThrowsException() {
        Throwable exception = assertThrows(InvalidGuidelineException.class, () -> {
            new TakingGuideline(dayMoment.AFTERBREAKFAST, 1.0f, 2.0f, 3.0f, FqUnit.DAY, "");
        });
        assertEquals("Instructions cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Null instructions throw InvalidGuidelineException")
    void nullInstructionsThrowsException() {
        Throwable exception = assertThrows(InvalidGuidelineException.class, () -> {
            new TakingGuideline(dayMoment.AFTERBREAKFAST, 1.0f, 2.0f, 3.0f, FqUnit.DAY, null);
        });
        assertEquals("Instructions cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Setting null day moment throws InvalidGuidelineException")
    void setInvalidDayMomentThrowsException() {
        TakingGuideline guide = new TakingGuideline(dayMoment.AFTERBREAKFAST, 1.0f, 2.0f, 3.0f, FqUnit.DAY, "Tomar despues de desayunar");
        Throwable exception = assertThrows(InvalidGuidelineException.class, () -> {
            guide.setdMoment(null);
        });
        assertEquals("Day moment cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Setting invalid duration throws InvalidGuidelineException")
    void setInvalidDurationThrowsException() {
        TakingGuideline guide = new TakingGuideline(dayMoment.AFTERBREAKFAST, 1.0f, 2.0f, 3.0f, FqUnit.DAY, "Tomar despues de desayunar");
        Throwable exception = assertThrows(InvalidGuidelineException.class, () -> {
            guide.setDuration(0f);
        });
        assertEquals("Duration cannot be 0 or less", exception.getMessage());
    }

    @Test
    @DisplayName("Setting empty instructions throws InvalidGuidelineException")
    void setEmptyInstructionsThrowsException() {
        TakingGuideline guide = new TakingGuideline(dayMoment.AFTERBREAKFAST, 1.0f, 2.0f, 3.0f, FqUnit.DAY, "Tomar despues de desayunar");
        Throwable exception = assertThrows(InvalidGuidelineException.class, () -> {
            guide.setInstructions("");
        });
        assertEquals("Instructions cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Invalid dose throws InvalidPosologyException")
    void invalidDoseThrowsInvalidPosologyException() {
        TakingGuideline guide = new TakingGuideline(dayMoment.AFTERBREAKFAST, 1.0f, 2.0f, 3.0f, FqUnit.DAY, "Tomar despues de desayunar");
        assertThrows(InvalidPosologyException.class, () -> {
            guide.setDose(0f);
        });
    }

    @Test
    @DisplayName("Invalid frequency throws InvalidPosologyException")
    void invalidFreqThrowsInvalidPosologyException() {
        TakingGuideline guide = new TakingGuideline(dayMoment.AFTERBREAKFAST, 1.0f, 2.0f, 3.0f, FqUnit.DAY, "Tomar despues de desayunar");
        assertThrows(InvalidPosologyException.class, () -> {
            guide.setFreq(0f);
        });
    }

    @Test
    @DisplayName("Invalid frequency unit throws InvalidPosologyException")
    void invalidFreqUnitThrowsInvalidPosologyException() {
        TakingGuideline guide = new TakingGuideline(dayMoment.AFTERBREAKFAST, 1.0f, 2.0f, 3.0f, FqUnit.DAY, "Tomar despues de desayunar");
        assertThrows(InvalidPosologyException.class, () -> {
            guide.setFreqUnit(null);
        });
    }
}


