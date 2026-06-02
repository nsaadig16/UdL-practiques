package org.example.services;

import org.example.data.HealthCardID;
import org.example.medicalconsultation.MedicalHistory;
import org.example.medicalconsultation.MedicalPrescription;
import org.example.services.exceptions.AnyCurrentPrescriptionException;
import org.example.services.exceptions.ConnectException;
import org.example.services.exceptions.HealthCardIDException;
import org.example.services.exceptions.NotCompletedMedicalPrescription;

public interface HealthNationalService {

    MedicalHistory getMedicalHistory(HealthCardID cip)
            throws ConnectException, HealthCardIDException;

    MedicalPrescription getMedicalPrescription(HealthCardID cip, String illness)
            throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException;

    MedicalPrescription sendHistoryAndPrescription(HealthCardID cip,
                                                   MedicalHistory hce, String illness, MedicalPrescription mPresc)
            throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException, NotCompletedMedicalPrescription;
}
