package org.example.medicalconsultation;

import org.example.data.DigitalSignature;
import org.example.data.HealthCardID;
import org.example.data.ProductID;
import org.example.data.exceptions.InvalidPersonalIDException;
import org.example.doubles.DecisionMakingAIMock;
import org.example.doubles.HealthNationalServiceMock;
import org.example.medicalconsultation.exceptions.*;
import org.example.services.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultationTerminalTest {

    private ConsultationTerminal ct;
    private int membershipNumber;
    private DigitalSignature signature;
    private HealthCardID cip;
    private String illness;
    private HealthNationalServiceMock hnsMock;
    private DecisionMakingAIMock aiMock;
    private ProductID product;
    private String[] instructions;

    @BeforeEach
    void setup() {
        cip = new HealthCardID("1234567890ABCDEF");
        illness = "Diabetes";
        membershipNumber = 1111;
        product = new ProductID("67".repeat(5));
        signature = new DigitalSignature(new byte[]{1, 1, 1, 1});
        instructions = new String[]{"BEFORELUNCH", "15", "1", "1", "DAY", "Drink water"};
        hnsMock = new HealthNationalServiceMock(membershipNumber);
        aiMock = new DecisionMakingAIMock();
        ct = new ConsultationTerminal(membershipNumber, signature);
        ct.setHNS(hnsMock);
        ct.setAI(aiMock);
    }

    @Test
    @DisplayName("A consultation terminal is created correctly with valid parameters")
    void testCreateConsultationTerminal() {
        assertNotNull(ct);
        assertEquals(membershipNumber, ct.getMembershipNum());
        assertEquals(signature, ct.getSignature());
    }

    @Test
    @DisplayName("Should throw NullDependencyException when setting null NHS")
    void testSetNullHNS() {
        ConsultationTerminal newTerminal = new ConsultationTerminal(membershipNumber, signature);

        Throwable exception = assertThrows(NullDependencyException.class, () -> {
            newTerminal.setHNS(null);
        });

        assertEquals("HNS dependency cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw NullDependencyException when setting null AI")
    void testSetNullAI() {
        ConsultationTerminal newTerminal = new ConsultationTerminal(membershipNumber, signature);

        Throwable exception = assertThrows(NullDependencyException.class, () -> {
            newTerminal.setAI(null);
        });

        assertEquals("AI dependency cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should initialize revision successfully with valid parameters")
    void testInitRevisionSuccess() {
        assertDoesNotThrow(() -> {
            ct.initRevision(cip, illness);
        });
    }

    @Test
    @DisplayName("Should throw InvalidPersonalIDException when CIP is null")
    void testInitRevisionNullCip() {
        Throwable exception = assertThrows(InvalidPersonalIDException.class, () -> {
            ct.initRevision(null, illness);
        });
        assertEquals("HealthCardID cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IncorrectParametersException when Illness is null")
    void testInitRevisionNullIllness() {
        Throwable exception = assertThrows(IncorrectParametersException.class, () -> {
            ct.initRevision(cip, null);
        });
        assertEquals("Illness cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw ConnectException when HNS connection fails")
    void testInitRevisionConnectException() {
        hnsMock.setThrowConnectException(true);

        assertThrows(ConnectException.class, () -> {
            ct.initRevision(cip, illness);
        });
    }

    @Test
    @DisplayName("Should throw HealthCardIDException when CIP is not registered")
    void testInitRevisionHealthCardIDException() {
        hnsMock.setHealthCardIDException(true);

        assertThrows(HealthCardIDException.class, () -> {
            ct.initRevision(cip, illness);
        });
    }

    @Test
    @DisplayName("Should throw AnyCurrentPrescriptionException when no prescription exists")
    void testInitRevisionAnyCurrentPrescriptionException() {
        hnsMock.setAnyCurrentPrescriptionException(true);

        assertThrows(AnyCurrentPrescriptionException.class, () -> {
            ct.initRevision(cip, illness);
        });
    }

    @Test
    @DisplayName("Should throw ProceduralException when CIP format is invalid")
    void testInitRevisionInvalidCIPFormat() {
        assertThrows(InvalidPersonalIDException.class, () -> {
            ct.initRevision(new HealthCardID("INVALID"), illness);
        });
    }

    @Test
    @DisplayName("Should throw IncorrectParametersException when illness is empty string")
    void testInitRevisionEmptyIllness() {
        assertThrows(IncorrectParametersException.class, () -> {
            ct.initRevision(cip, "");
        });
    }

    @Test
    @DisplayName("Should throw ProceduralException when entering assessment without initRevision")
    void testEnterMedicalAssessmentWithoutInit() {
        Throwable exception = assertThrows(ProceduralException.class, () -> {
            ct.enterMedicalAssessmentInHistory("Assessment");
        });
        assertEquals("History or prescription not stored in memory", exception.getMessage());
    }

    @Test
    @DisplayName("Should enter medical assessment successfully after initRevision")
    void testEnterMedicalAssessmentSuccess() {
        ct.initRevision(cip, illness);

        assertDoesNotThrow(() -> {
            ct.enterMedicalAssessmentInHistory("Paciente con síntomas de diabetes tipo 2");
        });
    }

    @Test
    @DisplayName("Should throw ProceduralException when entering null assessment")
    void testEnterNullAssessment() {
        ct.initRevision(cip, illness);

        assertThrows(IncorrectParametersException.class, () -> {
            ct.enterMedicalAssessmentInHistory(null);
        });
    }

    @Test
    @DisplayName("Should throw ProceduralException when entering assessment with empty assessment")
    void testEnterEmptyAssessment() {
        ct.initRevision(cip, illness);

        assertThrows(IncorrectParametersException.class, () -> {
            ct.enterMedicalAssessmentInHistory("");
        });
    }

    @Test
    @DisplayName("Should start prescription edition successfully after initRevision")
    void testInitMedicalPrescriptionEdition() {
        ct.initRevision(cip, illness);

        assertDoesNotThrow(() -> {
            ct.initMedicalPrescriptionEdition();
        });
    }

    @Test
    @DisplayName("Should throw ProceduralException when starting edition without prescription")
    void testInitMedicalPrescriptionEditionWithoutInit() {
        Throwable exception = assertThrows(ProceduralException.class, () -> {
            ct.initMedicalPrescriptionEdition();
        });

        assertEquals("Prescription is not stored in memory", exception.getMessage());
    }

    @Test
    @DisplayName("Should finish prescription edition successfully")
    void testFinishMedicalPrescriptionEdition() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();

        assertDoesNotThrow(() -> {
            ct.finishMedicalPrescriptionEdition();
        });
    }

    @Test
    @DisplayName("Should add medicine with guidelines successfully during edition")
    void testEnterMedicineWithGuidelinesSuccess() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();

        assertDoesNotThrow(() -> {
            ct.enterMedicineWithGuidelines(product, instructions);
        });
    }

    @Test
    @DisplayName("Should throw ProceduralException when adding medicine without starting edition")
    void testEnterMedicineWithoutEdition() {
        ct.initRevision(cip, illness);

        Throwable exception = assertThrows(ProceduralException.class, () -> {
            ct.enterMedicineWithGuidelines(product, instructions);
        });

        assertEquals("Prescription is not in edition", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw ProductAlreadyInPrescriptionException when adding duplicate medicine")
    void testEnterMedicineDuplicate() throws Exception {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();
        ct.enterMedicineWithGuidelines(product, instructions);

        assertThrows(ProductAlreadyInPrescriptionException.class, () -> {
            ct.enterMedicineWithGuidelines(product, instructions);
        });
    }

    @Test
    @DisplayName("Should throw IncorrectTakingGuidelinesException when instructions are invalid")
    void testEnterMedicineInvalidInstructions() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();

        String[] invalidInstructions = new String[]{"INVALID", "15", "1"};

        assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            ct.enterMedicineWithGuidelines(product, invalidInstructions);
        });
    }

    @Test
    @DisplayName("Should modify dose successfully during edition")
    void testModifyDoseSuccess() throws Exception {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();
        ct.enterMedicineWithGuidelines(product, instructions);

        assertDoesNotThrow(() -> {
            ct.modifyDoseInLine(product, 3.0f);
        });
    }

    @Test
    @DisplayName("Should throw ProceduralException when modifying dose without edition")
    void testModifyDoseWithoutEdition() {
        ct.initRevision(cip, illness);

        Throwable exception = assertThrows(ProceduralException.class, () -> {
            ct.modifyDoseInLine(product, 3.0f);
        });

        assertEquals("Prescription is not in edition", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw ProductNotInPrescriptionException when modifying non-existent product")
    void testModifyDoseNonExistentProduct() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();

        assertThrows(ProductNotInPrescriptionException.class, () -> {
            ct.modifyDoseInLine(product, 3.0f);
        });
    }

    @Test
    @DisplayName("Should remove line successfully during edition")
    void testRemoveLineSuccess() throws Exception {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();
        ct.enterMedicineWithGuidelines(product, instructions);

        assertDoesNotThrow(() -> {
            ct.removeLine(product);
        });
    }

    @Test
    @DisplayName("Should throw ProceduralException when removing line without edition")
    void testRemoveLineWithoutEdition() {
        ct.initRevision(cip, illness);

        Throwable exception = assertThrows(ProceduralException.class, () -> {
            ct.removeLine(product);
        });

        assertEquals("Prescription is not in edition", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw ProductNotInPrescriptionException when removing non-existent product")
    void testRemoveLineNonExistentProduct() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();

        assertThrows(ProductNotInPrescriptionException.class, () -> {
            ct.removeLine(product);
        });
    }

    @Test
    @DisplayName("Should set treatment ending date successfully")
    void testEnterTreatmentEndingDateSuccess() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();

        Date futureDate = new Date(System.currentTimeMillis() + 48 * 60 * 60 * 1000);

        assertDoesNotThrow(() -> {
            ct.enterTreatmentEndingDate(futureDate);
        });
    }

    @Test
    @DisplayName("Should throw ProceduralException when setting date without edition")
    void testEnterTreatmentEndingDateWithoutEdition() {
        ct.initRevision(cip, illness);

        Date futureDate = new Date(System.currentTimeMillis() + 48 * 60 * 60 * 1000);

        Throwable exception = assertThrows(ProceduralException.class, () -> {
            ct.enterTreatmentEndingDate(futureDate);
        });

        assertEquals("Prescription is not in edition", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IncorrectEndingDateException when date is in the past")
    void testEnterTreatmentEndingDatePast() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();

        Date pastDate = new Date(System.currentTimeMillis() - 1000);

        Throwable exception = assertThrows(IncorrectEndingDateException.class, () -> {
            ct.enterTreatmentEndingDate(pastDate);
        });

        assertEquals("The ending Date must be after the start Date.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IncorrectEndingDateException when date is less than 24 hours away")
    void testEnterTreatmentEndingDateTooSoon() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();

        Date nearDate = new Date(System.currentTimeMillis() + 12 * 60 * 60 * 1000);

        Throwable exception = assertThrows(IncorrectEndingDateException.class, () -> {
            ct.enterTreatmentEndingDate(nearDate);
        });

        assertEquals("The Dates should be at least 24 hours apart.", exception.getMessage());
    }

    @Test
    @DisplayName("Should stamp signature successfully after finishing edition and setting dates")
    void testStampeeSignatureSuccess() throws Exception {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();
        ct.enterMedicineWithGuidelines(product, instructions);

        Date futureDate = new Date(System.currentTimeMillis() + 48 * 60 * 60 * 1000);
        ct.enterTreatmentEndingDate(futureDate);
        ct.finishMedicalPrescriptionEdition();

        assertDoesNotThrow(() -> {
            ct.stampeeSignature();
        });
    }

    @Test
    @DisplayName("Should throw ProceduralException when stamping signature while still in edition")
    void testStampeeSignatureWhileEditing() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();

        Date futureDate = new Date(System.currentTimeMillis() + 48 * 60 * 60 * 1000);
        ct.enterTreatmentEndingDate(futureDate);

        Throwable exception = assertThrows(ProceduralException.class, () -> {
            ct.stampeeSignature();
        });

        assertTrue(exception.getMessage().contains("still in edition"));
    }

    @Test
    @DisplayName("Should throw ProceduralException when stamping signature without dates")
    void testStampeeSignatureWithoutDates() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();
        ct.finishMedicalPrescriptionEdition();

        Throwable exception = assertThrows(ProceduralException.class, () -> {
            ct.stampeeSignature();
        });

        assertTrue(exception.getMessage().contains("Treatment period not established"));
    }

    @Test
    @DisplayName("Should send history and prescription successfully when complete")
    void testSendHistoryAndPrescriptionSuccess() throws Exception {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();
        ct.enterMedicineWithGuidelines(product, instructions);

        Date futureDate = new Date(System.currentTimeMillis() + 48 * 60 * 60 * 1000);
        ct.enterTreatmentEndingDate(futureDate);
        ct.finishMedicalPrescriptionEdition();
        ct.stampeeSignature();

        MedicalPrescription result = ct.sendHistoryAndPrescription();

        assertNotNull(result);
        assertNotNull(result.getPrescCode());
    }

    @Test
    @DisplayName("Should throw NotCompletedMedicalPrescription when prescription is incomplete")
    void testSendHistoryAndPrescriptionIncomplete() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();
        ct.finishMedicalPrescriptionEdition();

        Throwable exception = assertThrows(NotCompletedMedicalPrescription.class, () -> {
            ct.sendHistoryAndPrescription();
        });

        assertEquals("Prescription not completed or still in edition", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw NotCompletedMedicalPrescription when sending while still in edition")
    void testSendHistoryAndPrescriptionWhileEditing() throws Exception {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();
        ct.enterMedicineWithGuidelines(product, instructions);
        Date futureDate = new Date(System.currentTimeMillis() + 48 * 60 * 60 * 1000);
        ct.enterTreatmentEndingDate(futureDate);

        Throwable exception = assertThrows(NotCompletedMedicalPrescription.class, () -> {
            ct.sendHistoryAndPrescription();
        });

        assertEquals("Prescription not completed or still in edition", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw NotCompletedMedicalPrescription when sending an incomplete prescription")
    void testSendHistoryAndPrescriptionNotCompletedException() throws Exception {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();
        ct.enterMedicineWithGuidelines(product, instructions);
        Date futureDate = new Date(System.currentTimeMillis() + 48 * 60 * 60 * 1000);
        ct.enterTreatmentEndingDate(futureDate);
        ct.finishMedicalPrescriptionEdition();

        assertThrows(NotCompletedMedicalPrescription.class, () -> {
            ct.sendHistoryAndPrescription();
        });
    }

    @Test
    @DisplayName("Should throw ConnectException when HNS connection fails during send")
    void testSendHistoryAndPrescriptionConnectException() throws Exception {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();
        ct.enterMedicineWithGuidelines(product, instructions);
        Date futureDate = new Date(System.currentTimeMillis() + 48 * 60 * 60 * 1000);
        ct.enterTreatmentEndingDate(futureDate);
        ct.finishMedicalPrescriptionEdition();
        ct.stampeeSignature();

        hnsMock.setThrowConnectException(true);

        assertThrows(ConnectException.class, () -> {
            ct.sendHistoryAndPrescription();
        });
    }


    @Test
    @DisplayName("Should call AI successfully during edition")
    void testCallDecisionMakingAISuccess() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();

        assertDoesNotThrow(() -> {
            ct.callDecisionMakingAI();
        });
    }

    @Test
    @DisplayName("Should throw ProceduralException when calling AI without edition")
    void testCallDecisionMakingAIWithoutEdition() {
        ct.initRevision(cip, illness);

        Throwable exception = assertThrows(ProceduralException.class, () -> {
            ct.callDecisionMakingAI();
        });

        assertEquals("Prescription is not in edition", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw AIException when AI initialization fails")
    void testCallDecisionMakingAIException() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();
        aiMock.setThrowAIException(true);

        assertThrows(AIException.class, () -> {
            ct.callDecisionMakingAI();
        });
    }

    @Test
    @DisplayName("Should ask AI for suggestions successfully")
    void testAskAIForSuggestSuccess() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();
        ct.callDecisionMakingAI();

        assertDoesNotThrow(() -> {
            ct.askAIForSuggest("What treatment do you recommend?");
        });
    }

    @Test
    @DisplayName("Should throw BadPromptException when prompt is invalid")
    void testAskAIForSuggestBadPrompt() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();
        ct.callDecisionMakingAI();

        aiMock.setThrowBadPromptException(true);

        assertThrows(BadPromptException.class, () -> {
            ct.askAIForSuggest("Invalid prompt");
        });
    }

    @Test
    @DisplayName("Should extract guidelines from suggestions successfully")
    void testExtractGuidelinesFromSuggSuccess() {
        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();
        ct.callDecisionMakingAI();
        ct.askAIForSuggest("What treatment do you recommend?");

        assertDoesNotThrow(() -> {
            ct.extractGuidelinesFromSugg();
        });
    }

    @Test
    @DisplayName("Should throw ProceduralException when extracting guidelines without edition")
    void testExtractGuidelinesWithoutEdition() {
        ct.initRevision(cip, illness);

        Throwable exception = assertThrows(ProceduralException.class, () -> {
            ct.extractGuidelinesFromSugg();
        });

        assertEquals("Prescription is not in edition", exception.getMessage());
    }
}
