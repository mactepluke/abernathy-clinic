package com.mediscreen.mhistory.service;

import com.mediscreen.mhistory.model.LightNote;
import com.mediscreen.mhistory.model.Note;
import com.mediscreen.mhistory.repository.NoteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Note add(String patientId,
                    String title,
                    String content) {

        Note note = new Note(
                patientId,
                title,
                LocalDateTime.now(),
                content
        );
        return noteRepository.insert(note);
    }

    @Transactional(readOnly = true)
    public List<LightNote> findAllNotes(String patientId) {
        return noteRepository.findByPatientIdOrderByDateTimeDesc(patientId);
    }

    @Transactional(readOnly = true)
    public Note findById(String id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Note update(String id,
                       String title,
                       String content) {

        Note note = findById(id);

        if (note != null) {
            note.setTitle(title);
            note.setContent(content);
            note.setDateTime(LocalDateTime.now());

            note = noteRepository.save(note);
        } else {
            log.warn("Note doest not exist with id: {}", id);
        }
        return note;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(String id) {
        noteRepository.deleteById(id);
    }
}
