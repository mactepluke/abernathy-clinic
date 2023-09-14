package com.mediscreen.mhistory.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@ToString
@Document(collection = "abernathy_clinic")
public class Note {
    @Id
    private String id;
    @Indexed()
    private String patientId;
    @Size(max = 100)
    private String title = "";
    private LocalDateTime dateTime;
    @Size(max = 8000)
    private String content = "";

    public Note()   {
    }

    public Note(String patientId,
                @NotNull String title,
                LocalDateTime dateTime,
                @NotNull String content)   {
        this.patientId = patientId;
        this.title = title;
        this.dateTime = dateTime;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public @NotNull String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public @NotNull String getContent() {
        return content;
    }

    public void setContent(@NotNull String content) {
        this.content = content;
    }
}
