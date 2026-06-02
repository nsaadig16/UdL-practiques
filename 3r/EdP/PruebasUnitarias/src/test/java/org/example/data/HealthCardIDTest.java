package org.example.data;

import org.example.data.exceptions.InvalidPersonalIDException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HealthCardIDTest {

    @Test
    @DisplayName("Should throw InvalidPersonalIDException when null")
    void testNullPersonalID() {
        Throwable exception = assertThrows(InvalidPersonalIDException.class, () -> {
            new HealthCardID(null);
        });
        assertEquals("Personal ID cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidPersonalIDE when length is not 16")
    void testWrongLengthPersonalID() {
        String[] wrongLengthIDs = {"", "0", "ABCDEFG123", "000000000000000", "12345678901234567"};
        for (String s : wrongLengthIDs) {
            Throwable exception = assertThrows(
                    InvalidPersonalIDException.class, () -> {
                        new HealthCardID(s);
                    }
            );
            assertEquals("Personal ID should be 16 characters long", exception.getMessage());
        }
    }

    @Test
    @DisplayName("Should throw InvalidPersonalIDException not alphanumerical")
    void testNotAlphanumericalPersonalID() {
        String[] notAlphanumericalIDs = {"!·$%&/!/(/&%%$$!", "montserrat$endin", "ÄÊÎòÁÈÓÙúÈŕǜńḿŝâ"};
        for (String s : notAlphanumericalIDs) {
            Throwable exception = assertThrows(
                    InvalidPersonalIDException.class, () -> {
                        new HealthCardID(s);
                    }
            );
            assertEquals("Personal ID should be alphanumerical", exception.getMessage());
        }
    }

    @Test
    @DisplayName("PersonalID is stored correctly")
    void testPersonalID() {
        String id = "1".repeat(16);
        HealthCardID healthCardID = new HealthCardID(id);
        assertEquals(id, healthCardID.getPersonalID());
    }
}
