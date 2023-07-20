package com.mediscreen.mpatients.web.controller;

import com.mediscreen.mpatients.model.Patient;
import com.mediscreen.mpatients.service.PatientService;
import com.mediscreen.mpatients.web.exceptions.CannotAddPatientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @PostMapping(value = "/add")
    public ResponseEntity<Patient>  add(
            @RequestParam String family,
            @RequestParam String given,
            @RequestParam String dob,
            @RequestParam char sex,
            @RequestParam Optional<String> address,
            @RequestParam Optional<String> phone
    )   {
        Patient patient = null;
        String addressValue = address.orElse("");
        String phoneValue = phone.orElse("");

        if(patient == null) throw new CannotAddPatientException("Cannot add patient.");

        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }
}
