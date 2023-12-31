package com.mediscreen.mpatients.service;

import com.mediscreen.mpatients.model.Patient;
import com.mediscreen.mpatients.repository.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class DefaultPatientService implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Patient add(String family, String given, LocalDate dob, char sex, String address, String phone) {

        if (patientRepository.findByFamilyAndGiven(family, given) != null)    {
            log.error("Patient already exists with family: '{}' and given: '{}'", family, given);
            return null;
        }

        Patient patient = new Patient(
                family,
                given,
                dob,
                sex,
                address,
                phone);

        return patientRepository.save(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Patient> findAll()  {
        return patientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Patient find(String family, String given)   {
        return patientRepository.findByFamilyAndGiven(family, given);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Patient update(String family,
                          String given,
                          String newFamily,
                          String newGiven,
                          LocalDate newDob,
                          char newSex,
                          String newAddress,
                          String newPhone) {

        Patient patient = find(family, given);

        if (patient != null)    {
            patient.setFamily(newFamily);
            patient.setGiven(newGiven);
            patient.setDob(newDob);
            patient.setSex(newSex);
            patient.setAddress(newAddress);
            patient.setPhone(newPhone);

            patient = patientRepository.save(patient);
        } else {
            log.warn("Patient doest not exist with family: '{}' and given: '{}'", family, given);
        }
        return patient;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Patient delete(String family, String given) {

        Patient patient = find(family, given);

        if (patient == null)    {
            log.warn("Patient doest not exist with family: '{}' and given: '{}'", family, given);
        } else {
            patientRepository.delete(patient);
        }
        return patient;
    }
}
