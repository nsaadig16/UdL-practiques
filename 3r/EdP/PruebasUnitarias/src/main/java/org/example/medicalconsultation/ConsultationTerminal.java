package org.example.medicalconsultation;

import org.example.data.DigitalSignature;
import org.example.data.HealthCardID;
import org.example.data.ProductID;
import org.example.data.exceptions.InvalidPersonalIDException;
import org.example.data.exceptions.InvalidProductIDException;
import org.example.medicalconsultation.exceptions.*;
import org.example.services.DecisionMakingAI;
import org.example.services.HealthNationalService;
import org.example.services.exceptions.*;

import java.util.Date;
import java.util.List;

public class ConsultationTerminal {

    private HealthNationalService hns;
    private DecisionMakingAI ai;
    private final int membershipNum;
    private final DigitalSignature signature;
    private MedicalHistory hce;
    private MedicalPrescription mPresc;
    private boolean isPrescriptionInEdition;
    private boolean isAIReady;
    private String prompt;
    private String aiAnswer;
    private List<Suggestion> suggestions;

    public ConsultationTerminal(int membershipNum, DigitalSignature signature) {
        this.membershipNum = membershipNum;
        this.signature = signature;
        this.isPrescriptionInEdition = false;
        this.isAIReady = false;
    }

    public void initRevision(HealthCardID cip, String illness) throws ConnectException,
            HealthCardIDException, AnyCurrentPrescriptionException, InvalidPersonalIDException,
            IncorrectParametersException {

        if (cip == null) {
            throw new InvalidPersonalIDException("HealthCardID cannot be null");
        }
        if (illness == null) {
            throw new IncorrectParametersException("Illness cannot be null or empty");
        }
        this.hce = hns.getMedicalHistory(cip);
        this.mPresc = hns.getMedicalPrescription(cip, illness);
    }

    public void enterMedicalAssessmentInHistory(String assess) throws IncorrectParametersException,
            ProceduralException {
        if (hce == null || mPresc == null) {
            throw new ProceduralException("History or prescription not stored in memory");
        }
        hce.addMedicalHistoryAnnotations(assess);
    }

    public void initMedicalPrescriptionEdition() {
        if (mPresc == null) {
            throw new ProceduralException("Prescription is not stored in memory");
        }
        this.isPrescriptionInEdition = true;
    }

    public void modifyDoseInLine(ProductID prodID, float newDose)
            throws ProductNotInPrescriptionException, IncorrectTakingGuidelinesException,
            ProceduralException {
        checkPrescriptionIsEditing();
        mPresc.modifyDoseInLine(prodID, newDose);
    }

    public void removeLine(ProductID prodID) throws
            ProductNotInPrescriptionException, InvalidProductIDException, ProceduralException {
        checkPrescriptionIsEditing();
        mPresc.removeLine(prodID);
    }

    public void enterMedicineWithGuidelines(ProductID prodID, String[] instruc) throws
            ProductAlreadyInPrescriptionException, IncorrectTakingGuidelinesException, ProceduralException {
        checkPrescriptionIsEditing();
        createMedPrescriptionLine(prodID, instruc);
    }

    public void enterTreatmentEndingDate(Date date) throws IncorrectEndingDateException, IncorrectParametersException,
            ProceduralException {
        checkPrescriptionIsEditing();
        setPrescDateAndEndDate(date);
    }

    public void finishMedicalPrescriptionEdition() {
        this.isPrescriptionInEdition = false;
    }

    public void stampeeSignature() throws eSignatureException, ProceduralException {
        if (isPrescriptionInEdition || !mPresc.isDateSet()) {
            throw new ProceduralException("Treatment period not established yet or prescription still in edition");
        }
        mPresc.seteSign(signature);
    }

    public MedicalPrescription sendHistoryAndPrescription() throws
            ConnectException, HealthCardIDException, AnyCurrentPrescriptionException, NotCompletedMedicalPrescription,
            ProceduralException {

        if (isPrescriptionInEdition || !mPresc.isComplete()) {
            throw new NotCompletedMedicalPrescription("Prescription not completed or still in edition");
        }

        HealthCardID cip = mPresc.getCip();
        String illness = mPresc.getIllness();
        MedicalPrescription newPresc = hns.sendHistoryAndPrescription(cip, hce, illness, mPresc);
        this.mPresc = newPresc;
        return newPresc;
    }

    public void callDecisionMakingAI() throws AIException, ProceduralException {
        checkPrescriptionIsEditing();
        ai.initDecisionMakingAI();
        this.isAIReady = true;
    }

    public void askAIForSuggest(String prompt) throws BadPromptException, ProceduralException {
        checkPrescriptionIsEditing();
        if (ai == null || !isAIReady) {
            throw new ProceduralException("AI is not ready");
        }
        this.prompt = prompt;
        aiAnswer = ai.getSuggestions(prompt);
    }

    public void extractGuidelinesFromSugg() throws BadPromptException, ProceduralException {
        checkPrescriptionIsEditing();
        askAIForSuggest(prompt);
        if (ai == null || !isAIReady || aiAnswer == null || aiAnswer.isBlank()) {
            throw new ProceduralException("No answer from the AI");
        }
        this.suggestions = ai.parseSuggest(aiAnswer);
        this.isAIReady = false;
    }

    public void printMedicalPrescrip() throws printingException {
    }

    private void createMedPrescriptionLine(ProductID prodID, String[] instruc) throws
            ProductAlreadyInPrescriptionException, IncorrectTakingGuidelinesException {
        mPresc.addLine(prodID, instruc);
    }

    private void setPrescDateAndEndDate(Date date) throws IncorrectEndingDateException, IncorrectParametersException {
        Date currentDate = new Date();
        if (currentDate.compareTo(date) >= 0) {
            throw new IncorrectEndingDateException("The ending Date must be after the start Date.");
        }
        long differenceInMillis = Math.abs(date.getTime() - currentDate.getTime());
        long differenceInHours = differenceInMillis / (1000 * 60 * 60);

        // We establish that the prescription must have a minimum duration of 24h
        if (differenceInHours < 24) {
            throw new IncorrectEndingDateException("The Dates should be at least 24 hours apart.");
        }
        mPresc.setPrescDate(currentDate);
        mPresc.setEndDate(date);
    }

    private void checkPrescriptionIsEditing() throws ProceduralException {
        if (!isPrescriptionInEdition) {
            throw new ProceduralException("Prescription is not in edition");
        }
    }

    // Setter methods for injecting dependencies
    public void setHNS(HealthNationalService hns) throws NullDependencyException {
        if (hns == null) {
            throw new NullDependencyException("HNS dependency cannot be null");
        }
        this.hns = hns;
    }

    public void setAI(DecisionMakingAI ai) {
        if (ai == null) {
            throw new NullDependencyException("AI dependency cannot be null");
        }
        this.ai = ai;
    }

    // Getters for testing
    int getMembershipNum() {
        return this.membershipNum;
    }

    DigitalSignature getSignature() {
        return this.signature;
    }

}
