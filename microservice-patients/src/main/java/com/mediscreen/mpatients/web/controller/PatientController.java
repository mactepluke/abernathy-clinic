package com.mediscreen.mpatients.web.controller;

import com.mediscreen.mpatients.model.Patient;
import com.mediscreen.mpatients.service.PatientService;
import com.mediscreen.mpatients.web.exceptions.CannotHandlePatientException;
import com.mediscreen.mpatients.web.exceptions.PatientNotFoundException;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/patient")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    @Autowired
    PatientService patientService;

    @PostMapping(value = "/add")
    public ResponseEntity<Patient>  add(
            @RequestParam @NotBlank String family,
            @RequestParam @NotBlank String given,
            @RequestParam @NotBlank @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
            @RequestParam @NotBlank char sex,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phone
    )   {

        Patient patient;

        log.info("Add request received with params: family={}, given={}, dob={}, sex={}, address={}, phone={}",
                family, given, dob, sex, address, phone);

        patient = patientService.add(family, given, dob, sex, address, phone);

        if (patient == null) throw new CannotHandlePatientException("Cannot add patient");

        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getall")
    public ResponseEntity<List<Patient>> getAll()   {

        List<Patient> patients;

        log.info("Get All request received");
        patients = patientService.findAll();

        if (patients == null) throw new PatientNotFoundException("Patient list was not found");

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Patient>  update(
            @RequestParam @NotBlank String family,
            @RequestParam @NotBlank String given,
            @RequestParam @NotBlank String newFamily,
            @RequestParam @NotBlank String newGiven,
            @RequestParam @NotBlank @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate newDob,
            @RequestParam @NotBlank char newSex,
            @RequestParam(required = false) String newAddress,
            @RequestParam(required = false) String newPhone
    )   {
        Patient patient;

        log.info("Udpate request received with params: family={}, given={}, dob={}, sex={}, address={}, phone={}",
                newFamily, newGiven, newDob, newSex, newAddress, newPhone);

        patient = patientService.update(family, given, newFamily, newGiven, newDob, newSex, newAddress, newPhone);

        if (patient == null) throw new CannotHandlePatientException("Cannot update patient");

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Patient>  delete(String family, String given)    {

        log.info("Delete request received with params: family={}, given={}", family, given);

        Patient patient = patientService.delete(family, given);

        if (patient == null) throw new PatientNotFoundException("Could not delete patient");

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

}
