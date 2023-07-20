package com.mediscreen.mpatients.repository;

import com.mediscreen.mpatients.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Patient findByFamilyAndGiven(String family, String given);
}
