package org.example.medicalconsultation;

import org.example.data.DigitalSignature;
import org.example.data.HealthCardID;
import org.example.data.ProductID;
import org.example.data.ePrescripCode;
import org.example.medicalconsultation.exceptions.IncorrectParametersException;
import org.example.medicalconsultation.exceptions.IncorrectTakingGuidelinesException;
import org.example.medicalconsultation.exceptions.ProductAlreadyInPrescriptionException;
import org.example.medicalconsultation.exceptions.ProductNotInPrescriptionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MedicalPrescriptionTest {

    private MedicalPrescription prescription;
    private HealthCardID cip;
    private ProductID product1;
    private ProductID product2;
    private String[] validInstructions;

    @BeforeEach
    void setUp() {
        cip = new HealthCardID("1234567890ABCDEF");
        prescription = new MedicalPrescription(cip, 12345, "Diabetes");

        product1 = new ProductID("1".repeat(10));
        product2 = new ProductID("2".repeat(10));

        // [dayMoment, duration, dose, freq, freqUnit, instructions]
        validInstructions = new String[]{
                "BEFORELUNCH",
                "15",
                "1",
                "1",
                "DAY",
                "Take with water"
        };
    }

    @Test
    @DisplayName("A medical prescription is created correctly with valid parameters")
    void testCreateMedicalPrescription() {
        assertNotNull(prescription);
        assertEquals(cip, prescription.getCip());
        assertEquals(12345, prescription.getMembShipNumb());
        assertEquals("Diabetes", prescription.getIllness());
        assertEquals(0, prescription.getLineCount());
    }

    @Test
    @DisplayName("Should throw IncorrectParametersException when constructor parameters are null or negative")
    void testIncorrectParametersConstructor() {
        assertThrows(IncorrectParametersException.class, () -> {
                    new MedicalPrescription(null, 12345, "Diabetes");
                }
        );
        assertThrows(IncorrectParametersException.class, () -> {
                    new MedicalPrescription(cip, -1, "Diabetes");
                }
        );
        assertThrows(IncorrectParametersException.class, () -> {
                    new MedicalPrescription(cip, 12345, null);
                }
        );
        assertThrows(IncorrectParametersException.class, () -> {
                    new MedicalPrescription(cip, 12345, "");
                }
        );
    }

    @Test
    @DisplayName("Adding a valid prescription line succeeds")
    void testAddLineSuccess() throws Exception {
        prescription.addLine(product1, validInstructions);

        assertEquals(1, prescription.getLineCount());
        assertTrue(prescription.getPrescriptionLines().containsKey(product1));

        TakingGuideline guideline = prescription.getPrescriptionLines().get(product1);
        assertNotNull(guideline);
        assertEquals(dayMoment.BEFORELUNCH, guideline.getdMoment());
        assertEquals(15, guideline.getDuration());
        assertEquals(1, guideline.getPosology().getDose());
    }

    @Test
    @DisplayName("Should throw ProductAlreadyInPrescriptionException when product already exists")
    void testAddLineProductAlreadyExists() throws Exception {
        prescription.addLine(product1, validInstructions);

        Throwable exception = assertThrows(
                ProductAlreadyInPrescriptionException.class,
                () -> prescription.addLine(product1, validInstructions)
        );
        assertEquals("Product " + product1.getProductID() + " is already in the prescription",
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IncorrectTakingGuidelinesException when adding null instructions")
    void testAddLineNullInstructions() {
        Throwable exception = assertThrows(
                IncorrectTakingGuidelinesException.class,
                () -> prescription.addLine(product1, null)
        );
        assertEquals("Instructions array must contain at least 6 elements: " +
                "[dayMoment, duration, dose, freq, freqUnit, instructions]", exception.getMessage()
        );
    }

    @Test
    @DisplayName("Should throw IncorrectTakingGuidelinesException when line is incomplete")
    void testAddLineIncompleteInstructions() {
        String[] incomplete = new String[]{"BEFORELUNCH", "15", "1"};

        Throwable exception = assertThrows(
                IncorrectTakingGuidelinesException.class,
                () -> prescription.addLine(product1, incomplete)
        );
        assertEquals("Instructions array must contain at least 6 elements: " +
                "[dayMoment, duration, dose, freq, freqUnit, instructions]", exception.getMessage()
        );
    }

    @Test
    @DisplayName("Should throw IncorrectTakingGuidelinesException when line contains an invalid day moment")
    void testAddLineInvalidDayMoment() {
        String[] invalid = new String[]{"INVALIDMOMENT", "15", "1", "1", "DAY", "test"};

        Throwable exception = assertThrows(
                IncorrectTakingGuidelinesException.class,
                () -> prescription.addLine(product1, invalid)
        );

        assertTrue(exception.getMessage().startsWith("Invalid format in taking guidelines:"));
    }

    @Test
    @DisplayName("Should throw IncorrectTakingGuidelinesException when numeric values are invalid")
    void testAddLineInvalidNumericValues() {
        String[][] invalidInstructions = {
                new String[]{"BEFORELUNCH", "abc", "1", "1", "DAY", "test"},
                new String[]{"BEFORELUNCH", "1", "abc", "1", "DAY", "test"},
                new String[]{"BEFORELUNCH", "1", "1", "abc", "DAY", "test"}
        };

        for (String[] invalid : invalidInstructions) {
            Throwable exception = assertThrows(
                    IncorrectTakingGuidelinesException.class,
                    () -> prescription.addLine(product1, invalid)
            );

            assertEquals("Invalid numeric values in taking guidelines", exception.getMessage());
        }
    }

    @Test
    @DisplayName("Should throw IncorrectTakingGuidelinesException when numeric values are negative")
    void testAddLineNegativeValues() {
        String[][] invalidInstructions = {
                new String[]{"BEFORELUNCH", "-15", "1", "1", "DAY", "test"},
                new String[]{"BEFORELUNCH", "1", "-15", "1", "DAY", "test"},
                new String[]{"BEFORELUNCH", "1", "1", "-15", "DAY", "test"}
        };

        for (String[] invalid : invalidInstructions) {
            Throwable exception = assertThrows(
                    IncorrectTakingGuidelinesException.class,
                    () -> prescription.addLine(product1, invalid)
            );
            assertTrue(exception.getMessage().contains("Duration, dose, and frequency must be positive values"));
        }
    }

    @Test
    @DisplayName("Modifying dose in an existing line succeeds")
    void testModifyDoseSuccess() throws Exception {
        prescription.addLine(product1, validInstructions);

        prescription.modifyDoseInLine(product1, 3.0f);

        TakingGuideline guideline = prescription.getPrescriptionLines().get(product1);
        assertEquals(3.0f, guideline.getPosology().getDose());
    }

    @Test
    @DisplayName("Should throw ProductNotInPrescriptionException when modifying dose for a non-existent product")
    void testModifyDoseProductNotFound() {
        Throwable exception = assertThrows(
                ProductNotInPrescriptionException.class,
                () -> prescription.modifyDoseInLine(product1, 3.0f)
        );

        assertEquals("Product " + product1.getProductID() + " is not in the prescription",
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when modifying dose with invalid value")
    void testModifyDoseInvalidValue() throws Exception {
        prescription.addLine(product1, validInstructions);

        Throwable exception = assertThrows(
                IncorrectTakingGuidelinesException.class,
                () -> prescription.modifyDoseInLine(product1, -1.0f)
        );
        assertEquals("Dose must be a positive value", exception.getMessage());
    }

    @Test
    @DisplayName("Removing an existing line succeeds")
    void testRemoveLineSuccess() throws Exception {
        prescription.addLine(product1, validInstructions);
        assertEquals(1, prescription.getLineCount());

        prescription.removeLine(product1);

        assertEquals(0, prescription.getLineCount());
        assertFalse(prescription.getPrescriptionLines().containsKey(product1));
    }

    @Test
    @DisplayName("Should throw ProductNotInPrescriptionException when removing a non-existent product")
    void testRemoveLineProductNotFound() {
        Throwable exception = assertThrows(
                ProductNotInPrescriptionException.class,
                () -> prescription.removeLine(product1)
        );

        assertEquals("Product " + product1.getProductID() + " is not in the prescription",
                exception.getMessage());
    }

    @Test
    @DisplayName("Managing multiple prescription lines works correctly")
    void testMultipleLinesManagement() throws Exception {
        // Add multiple lines
        prescription.addLine(product1, validInstructions);

        String[] instructions2 = new String[]{
                "AFTERDINNER", "30", "2", "2", "DAY", "Test text"
        };
        prescription.addLine(product2, instructions2);

        assertEquals(2, prescription.getLineCount());
        assertTrue(prescription.getPrescriptionLines().containsKey(product1));
        assertTrue(prescription.getPrescriptionLines().containsKey(product2));

        // Modify one
        prescription.modifyDoseInLine(product1, 5.0f);
        assertEquals(5.0f,
                prescription.getPrescriptionLines().get(product1)
                        .getPosology().getDose());

        // Remove one
        prescription.removeLine(product2);
        assertEquals(1, prescription.getLineCount());
        assertTrue(prescription.getPrescriptionLines().containsKey(product1));
        assertFalse(prescription.getPrescriptionLines().containsKey(product2));
    }

    @Test
    @DisplayName("Prescription is not complete when missing required fields")
    void testIsCompleteWhenIncomplete() {
        assertFalse(prescription.isComplete());
    }

    @Test
    @DisplayName("Prescription is complete when all required fields are set")
    void testIsCompleteWhenComplete() {
        prescription.setPrescDate(new Date());
        prescription.setEndDate(new Date());
        prescription.seteSign(new DigitalSignature(new byte[]{1, 2, 3}));
        prescription.setPrescCode(new ePrescripCode("A".repeat(20)));

        assertTrue(prescription.isComplete());
    }

    @Test
    @DisplayName("All setters and getters work correctly")
    void testSettersAndGetters() {
        Date prescDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + 86400000L);
        DigitalSignature signature = new DigitalSignature(new byte[]{1, 2, 3});
        ePrescripCode code = new ePrescripCode("A".repeat(20));

        prescription.setPrescDate(prescDate);
        prescription.setEndDate(endDate);
        prescription.seteSign(signature);
        prescription.setPrescCode(code);

        assertEquals(prescDate, prescription.getPrescDate());
        assertEquals(endDate, prescription.getEndDate());
        assertEquals(signature, prescription.geteSign());
        assertEquals(code, prescription.getPrescCode());
    }

    @Test
    @DisplayName("Should throw IncorrectParametersException when setter parameters are null")
    void testNullParameters() {
        assertThrows(IncorrectParametersException.class, () -> {
            prescription.seteSign(null);
        });
        assertThrows(IncorrectParametersException.class, () -> {
            prescription.setEndDate(null);
        });
        assertThrows(IncorrectParametersException.class, () -> {
            prescription.setPrescDate(null);
        });
        assertThrows(IncorrectParametersException.class, () -> {
            prescription.setPrescCode(null);
        });
    }

    @Test
    @DisplayName("Different day moments are handled correctly in prescription lines")
    void testDifferentDayMoments() throws Exception {
        String[] instructions1 = new String[]{"BEFOREBREAKFAST", "7", "1", "1", "DAY", "En ayunas"};
        String[] instructions2 = new String[]{"AFTERDINNER", "14", "2", "1", "DAY", "Antes de dormir"};

        prescription.addLine(product1, instructions1);
        prescription.addLine(product2, instructions2);

        assertEquals(dayMoment.BEFOREBREAKFAST,
                prescription.getPrescriptionLines().get(product1).getdMoment());
        assertEquals(dayMoment.AFTERDINNER,
                prescription.getPrescriptionLines().get(product2).getdMoment());
    }

    @Test
    @DisplayName("Different frequency units are handled correctly in prescription lines")
    void testDifferentFrequencyUnits() throws Exception {
        String[] instructionsHour = new String[]{"BEFORELUNCH", "3", "1", "8", "HOUR", "Cada 8 horas"};

        prescription.addLine(product1, instructionsHour);

        TakingGuideline guideline = prescription.getPrescriptionLines().get(product1);
        assertEquals(FqUnit.HOUR, guideline.getPosology().getFreqUnit());
        assertEquals(8, guideline.getPosology().getFreq());
    }
}