package com.mediscreen.massessment.web.controller;

import com.mediscreen.massessment.model.DiabetesRiskLevel;
import com.mediscreen.massessment.service.AssessmentService;
import com.mediscreen.massessment.web.exceptions.CannotHandleAssessmentException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;


@Log4j2
@RestController
@RequestMapping("/assessment")
@Validated
@Scope("request")
@AllArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @GetMapping(value = "/assessDiabetesRisk")
    public ResponseEntity<DiabetesRiskLevel> assessDiabetesRisk(@Valid @RequestParam @NotBlank String patientId,
                                                                @RequestParam char sex,
                                                                @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob)    {

        DiabetesRiskLevel assessment;

        log.info("Diabetes assessment request received with params: patientId={}, sex={}, dob={}",
                patientId, sex, dob);

        assessment = assessmentService.assessDiabetesRisk(patientId, sex, dob);

        if (assessment == null || assessment == DiabetesRiskLevel.UNKNOWN) throw new CannotHandleAssessmentException("Could not assess diabetes risk for patientId="
                + patientId
                + " (assessment="
                + assessment
                + ")");

        return new ResponseEntity<>(assessment, HttpStatus.OK);
    }

}
