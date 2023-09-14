package com.mediscreen.mhistory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateNoteDTO {
    @NotBlank
    private String id;
    @Size(max = 100)
    @NotBlank
    private String title;
    @Size(max = 8000)
    @NotBlank
    private String content;
}
