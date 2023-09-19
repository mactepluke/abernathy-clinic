package com.mediscreen.mhistory.service;

import com.mediscreen.mhistory.model.LightNote;
import com.mediscreen.mhistory.model.Note;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HistoryService {
    /**
     * This persists a note in the NoSql database
     * @param patientId the id of the patient about whom the note is
     * @param title the title of the note
     * @param content the content of the note
     * @return the Note object that has been persisted
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    Note add(String patientId,
             String title,
             String content);

    /**
     * This finds all notes in a list of simple 'LightNote' objects in the database
     * that do not contain the note 'content', but only their id, title and update/creation date.
     * This can be used to lazy load the notes when they must be used without their content,
     * for example for display as a list in a table
     * @param patientId the id of the patient whose related note must be found
     * @return the list of 'LightNote' objects
     */
    @Transactional(readOnly = true)
    List<LightNote> findAllNotes(String patientId);

    /**
     * This finds all notes in full 'Note' objects in the database that include all the fields related to a note,
     * including its content.
     * @param patientId the id of the patient whose related note must be found
     * @return the list of 'Note' objects
     */
    @Transactional(readOnly = true)
    List<Note> findAllNotesWithContent(String patientId);

    /**
     * This finds a note by its unique id in the database
     * @param id the id of the note
     * @return the 'Note' object found
     */
    @Transactional(readOnly = true)
    Note findById(String id);

    /**
     * This updates a note of the corresponding id in the database with a new title and a new content
     * @param id the id of the note to update
     * @param title the new title
     * @param content the new content
     * @return the updated 'Note' object that has been persisted
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    Note update(String id,
                String title,
                String content);

    /**
     * This deletes a single note in the database
     * @param id the id of the note to delete
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    void delete(String id);

    /**
     * This deletes all notes related to a single patient in the database.
     * It is used upon the deletion of a patient so 'orphan notes' are removed.
     * @param patientId the id of the patient whose notes must be deleted
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    void deleteAll(String patientId);
}
