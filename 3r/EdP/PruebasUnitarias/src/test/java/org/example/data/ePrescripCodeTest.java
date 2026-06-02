package org.example.data;

import org.example.data.exceptions.InvalidEPrescripCodeException;
import org.example.data.exceptions.InvalidPersonalIDException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ePrescripCodeTest {
    @Test
    @DisplayName("Should throw InvalidEPrescripCode when null")
    void testNullPrescrip() {
        Throwable exception = assertThrows(InvalidEPrescripCodeException.class, () -> {
            new ePrescripCode(null);
        });
        assertEquals("ePrescripCode cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidEPrescripCode when empty")
    void testEmptyEPrescripCode() {
        Throwable exception = assertThrows(InvalidEPrescripCodeException.class, () -> {
            new ePrescripCode("");
        });
        assertEquals("ePrescripCode cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidEPrescripCodeException if length is not 20")
    void testWrongLengthEPrescripCode() {
        Throwable exception1 = assertThrows(InvalidEPrescripCodeException.class, () -> {
            new ePrescripCode("67".repeat(3));
        });
        Throwable exception2 = assertThrows(InvalidEPrescripCodeException.class, () -> {
            new ePrescripCode("67".repeat(67));
        });
        assertEquals("ePrescripCode should be 20 characters", exception1.getMessage());
        assertEquals("ePrescripCode should be 20 characters", exception2.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidEPrescripCodeException not alphanumerical")
    void testNotAlphanumericalEPrescripCode() {
        String[] invalidEPrescripCode = {"!·$%&/!/(/&%%$$!8888", "montserratsendin676*", "ÄÊÎòÁÈÓÙúÈŕǜńḿŝâamen"};
        for (String s : invalidEPrescripCode) {
            Throwable exception = assertThrows(
                    InvalidEPrescripCodeException.class, () -> {
                        new ePrescripCode(s);
                    }
            );
            assertEquals("ePrescripCode should be alphanumerical", exception.getMessage());
        }
    }

    @Test
    @DisplayName("PrescripCode is stored correctly")
    void testPrescripCodeStored() {
        String code = "A".repeat(20);
        ePrescripCode ePrescripCode = new ePrescripCode(code);
        assertEquals(code, ePrescripCode.getePrescripCode());
    }

}
