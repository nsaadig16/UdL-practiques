package org.example.doubles;

import org.example.data.HealthCardID;
import org.example.data.ePrescripCode;
import org.example.medicalconsultation.MedicalHistory;
import org.example.medicalconsultation.MedicalPrescription;
import org.example.services.HealthNationalService;
import org.example.services.exceptions.AnyCurrentPrescriptionException;
import org.example.services.exceptions.ConnectException;
import org.example.services.exceptions.HealthCardIDException;
import org.example.services.exceptions.NotCompletedMedicalPrescription;

public class HealthNationalServiceMock implements HealthNationalService {
    public boolean connectException;
    public boolean idException;
    public boolean currentPrescriptionException;
    public int membershipNumber;

    public HealthNationalServiceMock(int membershipNumber) {
        this.connectException = false;
        this.idException = false;
        this.currentPrescriptionException = false;
        this.membershipNumber = membershipNumber;
    }

    @Override
    public MedicalHistory getMedicalHistory(HealthCardID cip) throws ConnectException, HealthCardIDException {
        if (connectException) {
            throw new ConnectException("Error connecting to the network");
        }
        if (idException) {
            throw new HealthCardIDException("ID is not registered in the HNS");
        }
        return new MedicalHistory(cip, membershipNumber);
    }

    @Override
    public MedicalPrescription getMedicalPrescription(HealthCardID cip, String illness) throws ConnectException,
            HealthCardIDException, AnyCurrentPrescriptionException {
        if (connectException) {
            throw new ConnectException("Error connecting to the network");
        }
        if (idException) {
            throw new HealthCardIDException("ID is not registered in the HNS");
        }
        if (currentPrescriptionException) {
            throw new AnyCurrentPrescriptionException("No medical prescription associated to the illness for this patient");
        }
        return new MedicalPrescription(cip, membershipNumber, illness);
    }

    @Override
    public MedicalPrescription sendHistoryAndPrescription(HealthCardID cip, MedicalHistory hce, String illness, MedicalPrescription mPresc)
            throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException, NotCompletedMedicalPrescription {
        if (connectException) {
            throw new ConnectException("Error connecting to the network");
        }
        if (idException) {
            throw new HealthCardIDException("ID is not registered in the HNS");
        }
        if (currentPrescriptionException) {
            throw new AnyCurrentPrescriptionException("No medical prescription associated to the illness for this patient");
        }
        if (!mPresc.isComplete()) {
            throw new NotCompletedMedicalPrescription("Prescription cannot be validated");
        }
        return generateTreatmCodeAndRegister(mPresc);
    }

    private MedicalPrescription generateTreatmCodeAndRegister(MedicalPrescription ePresc) throws ConnectException {
        if (connectException) {
            throw new ConnectException("Error connecting to the network");
        }
        ePrescripCode presscode = new ePrescripCode("A".repeat(20));
        ePresc.setPrescCode(presscode);
        return ePresc;
    }

    public void setThrowConnectException(boolean b) {
        this.connectException = b;
    }

    public void setHealthCardIDException(boolean b) {
        this.idException = b;
    }

    public void setAnyCurrentPrescriptionException(boolean b) {
        this.currentPrescriptionException = b;
    }
}

