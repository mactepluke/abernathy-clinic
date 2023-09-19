package com.mediscreen.mpatients.service;

import com.mediscreen.mpatients.model.Patient;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface PatientService {

    /**
     * This persists a new patient in the database if not already present
     * @param family the family name of the patient
     * @param given the given name (or first name) of the patient
     * @param dob the date of birth of the patient
     * @param sex the sex of the patient
     * @param address the postal address of the patient
     * @param phone the phone number of a patient
     * @return a 'Patient' object that matches the newly persisted patient
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    Patient add(String family, String given, LocalDate dob, char sex, String address, String phone);

    /**
     * This finds all the patients present in the database
     * @return a list of 'Patient' objects found
     */
    @Transactional(readOnly = true)
    List<Patient> findAll();


    /**
     * This finds a specific patient in the database
     * @param family the family name of the patient to be found
     * @param given the given name (or first name) of the patient to be found
     * @return the 'Patient' object of the patient that has been found
     */
    @Transactional(readOnly = true)
    Patient find(String family, String given);

    /**
     * This update a specific patient with new information in the database
     * @param family the family name of the patient to be updated
     * @param given the given name (or first name) of the patient to be updated
     * @param newFamily the new family name that the patient must be updated with
     * @param newGiven the new given name (or first name) that the patient must be updated with
     * @param newDob the new date of birth that the patient must be updated with
     * @param newSex the new sex that the patient must be updated with
     * @param newAddress the new postal address that the patient must be updated with
     * @param newPhone the new phone number that the patient must be updated with
     * @return a 'Patient' object that corresponds to the updated patient entry in the database
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    Patient update(String family,
                   String given,
                   String newFamily,
                   String newGiven,
                   LocalDate newDob,
                   char newSex,
                   String newAddress,
                   String newPhone);

    /**
     * This deletes a specific patient in the database
     * @param family the family name of the patient to delete
     * @param given the given name (or first name) of the patient to delete
     * @return the 'Patient' object that matches the patient entry that has been deleted
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    Patient delete(String family, String given);
}
