package com.mediscreen.massessment.proxies;

import com.mediscreen.massessment.dto.NoteContentDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "history", url = "${MHISTORY_URI:http://localhost:8082}")
public interface PatientHistoryApiProxy {

    @GetMapping("/history/getFullHistory")
    List<NoteContentDTO> getFullHistory(@Valid @RequestParam @NotBlank String patientId);
}
