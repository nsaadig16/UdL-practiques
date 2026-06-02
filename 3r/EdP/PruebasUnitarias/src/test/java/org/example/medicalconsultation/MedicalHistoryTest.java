package org.example.medicalconsultation;

import org.example.data.HealthCardID;
import org.example.medicalconsultation.exceptions.IncorrectParametersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MedicalHistoryTest {

    private HealthCardID cip;
    private int membshipNum = 1450;
    private MedicalHistory medicalHistory;

    @BeforeEach
    void setup() {
        cip = new HealthCardID("1234567890123456");
        membshipNum = 1450;
        medicalHistory = new MedicalHistory(cip, membshipNum);
    }

    @Test
    @DisplayName("Should throw IncorrectParametersException when CIP is null")
    void testNullCIP() {
        Throwable exception = assertThrows(
                IncorrectParametersException.class, () -> {
                    new MedicalHistory(null, membshipNum);
                }
        );
        assertEquals("The CIP cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IncorrectParametersException when membership number is negative")
    void testNegativeMembershipNum() {
        Throwable exception = assertThrows(
                IncorrectParametersException.class, () -> {
                    new MedicalHistory(cip, -1);
                }
        );
        assertEquals("The membership number cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should return the CIP")
    void testGetCIP() {
        assertEquals(cip, medicalHistory.getCip());
    }

    @Test
    @DisplayName("Should return the membership number")
    void testGetMembershipNum() {
        assertEquals(membshipNum, medicalHistory.getMembShipNumb());
    }

    @Test
    @DisplayName("Should return empty history in new object")
    void testEmptyHistory() {
        assertEquals("", medicalHistory.getHistory());
    }

    @Test
    @DisplayName("Should return the added annotation")
    void testAddAnnotation() {
        String annot = "Lorem ipsum dolor sit amet.";
        medicalHistory.addMedicalHistoryAnnotations(annot);
        assertEquals(annot, medicalHistory.getHistory());
    }

    @Test
    @DisplayName("Should return the concatenation of several annotations")
    void testAddSeveralAnnotations() {
        String[] annotations = new String[]{"1", "2", "3", "4", "5"};
        for (String a : annotations) {
            medicalHistory.addMedicalHistoryAnnotations(a);
        }
        assertEquals("12345", medicalHistory.getHistory());
    }

    @Test
    @DisplayName("Should throw IncorrectParametersException when annotation is null")
    void testNullAnnotation() {
        Throwable exception = assertThrows(
                IncorrectParametersException.class, () -> {
                    medicalHistory.addMedicalHistoryAnnotations(null);
                }
        );
        assertEquals("An annotation cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should change the membership number")
    void testSetNewDoctor() {
        medicalHistory.setNewDoctor(1234);
        assertEquals(1234, medicalHistory.getMembShipNumb());
    }

    @Test
    @DisplayName("Should throw IncorrectParametersException when setting a negative membership number")
    void testSetNegativeMembershipNum() {
        Throwable exception = assertThrows(IncorrectParametersException.class, () -> {
            medicalHistory.setNewDoctor(-1);
        });
        assertEquals("The membership number cannot be negative", exception.getMessage());
    }
}
