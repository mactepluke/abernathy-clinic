package com.mediscreen.mhistory.web.controller;

import com.mediscreen.mhistory.dto.AddNoteDTO;
import com.mediscreen.mhistory.dto.UpdateNoteDTO;
import com.mediscreen.mhistory.model.LightNote;
import com.mediscreen.mhistory.model.Note;
import com.mediscreen.mhistory.service.HistoryService;
import com.mediscreen.mhistory.web.exceptions.CannotHandleNoteException;
import com.mediscreen.mhistory.web.exceptions.NoteNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Math.min;

@Log4j2
@RestController
@RequestMapping("/history")
@Validated
@Scope("request")
@AllArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @PostMapping(value = "/addNote")
    public ResponseEntity<Note> addNote(
            @Valid
            @RequestBody AddNoteDTO noteToAdd
            )   {

        Note note;
        String shortContent = shortenContent(noteToAdd.getContent());

        log.info("Add request received with params: patientId={}, title={}, content={}...",
                noteToAdd.getPatientId(), noteToAdd.getTitle(), shortContent);

        note = historyService.add(noteToAdd.getPatientId(), noteToAdd.getTitle(), noteToAdd.getContent());

        if (note == null) throw new CannotHandleNoteException("Cannot add note");

        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getNote")
    public ResponseEntity<Note> getNote(@RequestParam @NotBlank String id)   {

        Note note;

        log.info("Get request received with params: id={}", id);
        note = historyService.findById(id);

        if (note == null) throw new NoteNotFoundException("Note was not found");

        return new ResponseEntity<>(note, HttpStatus.OK);
    }


    @GetMapping(value = "/getHistory")
    public ResponseEntity<List<LightNote>> getAll(@RequestParam @NotBlank String patientId)   {

        List<LightNote> history;

        if (patientId == null
                || patientId.isEmpty()
                || patientId.equals("undefined")) throw new CannotHandleNoteException("PatientId is invalid");

        log.info("Get history request received with patientId={}", patientId);
        history = historyService.findAllNotes(patientId);

        if (history == null) throw new NoteNotFoundException("Patient history was not found");

        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @GetMapping(value = "/getFullHistory")
    public ResponseEntity<List<Note>> getAllWithContent(@RequestParam @NotBlank String patientId)   {

        List<Note> history;

        if (patientId == null
                || patientId.isEmpty()
                || patientId.equals("undefined")) throw new CannotHandleNoteException("PatientId is invalid");

        log.info("Get full history request received with patientId={}", patientId);
        history = historyService.findAllNotesWithContent(patientId);

        if (history == null) throw new NoteNotFoundException("Patient history was not found");

        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @PutMapping(value = "/updateNote")
    public ResponseEntity<Note>  updateNote(
            @Valid
            @RequestBody UpdateNoteDTO updatedNote
    )   {

        Note note;

        String shortContent = shortenContent(updatedNote.getContent());

        log.info("Udpate request received with params: id={}, title={}, content={}",
                updatedNote.getId(), updatedNote.getTitle(), shortContent);

        note = historyService.update(updatedNote.getId(), updatedNote.getTitle(), updatedNote.getContent());

        if (note == null) throw new CannotHandleNoteException("Cannot update note");

        return new ResponseEntity<>(note, HttpStatus.OK);
    }


    @DeleteMapping(value = "/deleteNote")
    public HttpStatus deleteNote(@RequestParam @NotBlank String id)    {

        log.info("Delete request received with params: id={}", id);

        historyService.delete(id);

        return HttpStatus.OK;
    }

    @DeleteMapping(value = "/deleteAllNotes")
    public HttpStatus deleteAllNotes(@RequestParam @NotBlank String patientId)    {

        log.info("Delete request received with params: patientId={}", patientId);

        historyService.deleteAll(patientId);

        return HttpStatus.OK;
    }

    private String shortenContent(String content)  {
        return content.length() > 0 ? content.substring(0, min(20, content.length() - 1)) : "";
    }
}
