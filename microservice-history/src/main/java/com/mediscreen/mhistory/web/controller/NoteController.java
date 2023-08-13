package com.mediscreen.mhistory.web.controller;

import com.mediscreen.mhistory.model.Note;
import com.mediscreen.mhistory.service.NoteService;
import com.mediscreen.mhistory.web.exceptions.CannotHandleNoteException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.lang.Math.min;

@Log4j2
@RestController
@RequestMapping("/history")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class NoteController {

    @Autowired
    NoteService noteService;

    @PostMapping(value = "/addNote")
    public ResponseEntity<Note> add(
            @Valid
            @RequestParam @NotBlank String patientId,
            @RequestParam @NotNull String title,
            @RequestParam @NotNull String content
    )   {

        Note note;
        String shortContent = content.length() > 0 ? content.substring(0, min(20, content.length() - 1)) : "";

        log.info("Add request received with params: patientId={}, title={}, content={}...",
                patientId, title, shortContent);

        note = noteService.add(patientId, title, content);

        if (note == null) throw new CannotHandleNoteException("Cannot add note");

        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    //TODO Implement CRUD methods in rest controller

    /*@GetMapping(value = "/get")
    public ResponseEntity<Patient> get(
            @Valid
            @RequestParam @NotBlank String family,
            @RequestParam @NotBlank String given
    )   {

        Patient patient;

        log.info("Get request received with params: family={}, given={}", family, given);
        patient = patientService.find(family, given);

        if (patient == null) throw new PatientNotFoundException("Patient was not found");

        return new ResponseEntity<>(patient, HttpStatus.OK);
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
            @Valid
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
    public ResponseEntity<Patient> delete(
            @Valid
            @RequestParam @NotBlank String family,
            @RequestParam @NotBlank String given
    )    {

        log.info("Delete request received with params: family={}, given={}", family, given);

        Patient patient = patientService.delete(family, given);

        if (patient == null) throw new PatientNotFoundException("Could not delete patient");

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }*/
}
