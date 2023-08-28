package com.mediscreen.mhistory.repository;

import com.mediscreen.mhistory.model.LightNote;
import com.mediscreen.mhistory.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends MongoRepository<Note, String> {

    List<LightNote> findByPatientIdOrderByDateTimeDesc(String id);

    void deleteAllByPatientId(String patientId);

    List<Note> findByPatientId(String patientId);
}
