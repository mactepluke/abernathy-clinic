package com.mediscreen.massessment.service;

import com.mediscreen.massessment.model.DiabetesRiskLevel;

import java.time.LocalDate;

public interface AssessmentService {

    /**
     * The assessment algorithm that calculates the risk of developing a Type 2 diabetes disease
     * @param patientId the id of the patient on whom to perform the assessment
     * @param sex the sex of the patient
     * @param dob the date of birth of the patient
     * @return an object that corresponds to the level of risk
     */
    DiabetesRiskLevel assessDiabetesRisk(String patientId, char sex, LocalDate dob);
}
