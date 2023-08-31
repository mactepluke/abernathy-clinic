package com.mediscreen.mhistory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateNoteDTO {
    @NotBlank
    private String id;
    @Size(max = 100)
    @NotNull
    private String title;
    @Size(max = 8000)
    @NotNull
    private String content;
}
