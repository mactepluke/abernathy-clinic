package com.mediscreen.mhistory.service;

import com.mediscreen.mhistory.model.LightNote;
import com.mediscreen.mhistory.model.Note;
import com.mediscreen.mhistory.repository.HistoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
public class HistoryService {

    @Autowired
    HistoryRepository historyRepository;

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
        return historyRepository.insert(note);
    }

    @Transactional(readOnly = true)
    public List<LightNote> findAllNotes(String patientId) {
        return historyRepository.findByPatientIdOrderByDateTimeDesc(patientId);
    }

    @Transactional(readOnly = true)
    public List<Note> findAllNotesWithContent(String patientId) {
        return historyRepository.findByPatientId(patientId);
    }

    @Transactional(readOnly = true)
    public Note findById(String id) {
        return historyRepository.findById(id).orElse(null);
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

            note = historyRepository.save(note);
        } else {
            log.warn("Note doest not exist with id: {}", id);
        }
        return note;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(String id) {
        historyRepository.deleteById(id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteAll(String patientId) {
        historyRepository.deleteAllByPatientId(patientId);
    }

}
