package com.mediscreen.massessment.service;

import com.mediscreen.massessment.dto.NoteContentDTO;
import com.mediscreen.massessment.model.DiabetesRiskLevel;
import com.mediscreen.massessment.proxies.PatientHistoryApiProxy;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.temporal.ChronoUnit.YEARS;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

@Log4j2
@Service
public class DefaultAssessmentService implements AssessmentService {
    @Value("#{'${triggering_terms:hemoglobine A1C,microalbumine,taille,poids,fumeur,anormal,cholesterol,vertige,rechute,reaction,anticorps,hemoglobin A1C,microalbumin,weight,height,smoker,abnormal,vertigo,relapse,antibody}'.split(',')}")
    private List<String> triggeringTerms;
    @Value("${risk_age_threshold:30}")
    private int riskAgeThreshold;
    @Value("${borderline_risk_trigger_threshold:2}")
    private int borderlineRiskTriggerThreshold;
    @Value("${in_danger_young_male_trigger_threshold:3}")
    private int inDangerYoungMaleTriggerThreshold;
    @Value("${in_danger_young_female_trigger_threshold:4}")
    private int inDangerYoungFemaleTriggerThreshold;
    @Value("${in_danger_old_trigger_threshold:6}")
    private int inDangerOldTriggerThreshold;
    @Value("${early_onset_young_male_trigger_threshold:5}")
    private int earlyOnsetYoungMaleTriggerThreshold;
    @Value("${early_onset_young_female_trigger_threshold:7}")
    private int earlyOnsetYoungFemaleTriggerThreshold;
    @Value("${early_onset_old_trigger_threshold:8}")
    private int earlyOnsetOldTriggerThreshold;


    private final PatientHistoryApiProxy patientHistoryApiProxy;

    public DefaultAssessmentService(PatientHistoryApiProxy patientHistoryApiProxy) {
        this.patientHistoryApiProxy = patientHistoryApiProxy;
    }

    @Override
    public DiabetesRiskLevel assessDiabetesRisk(String patientId, char sex, LocalDate dob)  {

        DiabetesRiskLevel diabetesRiskLevel;
        int triggers;

        List<NoteContentDTO> noteContentsDTO = this.patientHistoryApiProxy.getFullHistory(patientId);

        if (noteContentsDTO == null || this.triggeringTerms.isEmpty()) {
            return DiabetesRiskLevel.UNKNOWN;
        }

        triggers = countTriggersInNoteContentList(noteContentsDTO);

        log.debug("Triggers= {}", triggers);

        diabetesRiskLevel = calculateRisk(triggers, sex, dob);

        return diabetesRiskLevel;
    }

    private int countTriggersInNoteContentList(@NotNull List<NoteContentDTO> noteContentsDTO) {

        AtomicInteger triggers = new AtomicInteger();

        if (noteContentsDTO.isEmpty()) {
            return 0;
        }

        noteContentsDTO.forEach(noteContent ->
                triggers.set(triggers.get() + countTriggersInNoteContent(StringUtils.stripAccents(noteContent.getContent()))));

        return triggers.get();
    }

    private int countTriggersInNoteContent(String content) {

        AtomicInteger triggers = new AtomicInteger();

        triggeringTerms.forEach(triggeringTerm -> {
            if (containsIgnoreCase(content, triggeringTerm)) {
                log.debug("Trigger={}", triggeringTerm);
                triggers.getAndIncrement();
            }
        });
        return triggers.get();
    }

    private DiabetesRiskLevel calculateRisk(int triggers, char sex, LocalDate dob) {

        boolean isMale;
        boolean isYoung = false;

        if (triggers < borderlineRiskTriggerThreshold) {
            return DiabetesRiskLevel.NONE;
        }

        if (triggers >= earlyOnsetOldTriggerThreshold) {
            return DiabetesRiskLevel.EARLY_ONSET;
        }

        switch (sex) {
            case 'M' -> isMale = true;
            case 'F' -> isMale = false;
            default -> {
                return DiabetesRiskLevel.UNKNOWN;
            }
        }

        int age = calculateAge(dob);

        if (age < 0) {
            return DiabetesRiskLevel.UNKNOWN;
        }

        if (age < riskAgeThreshold) {
            isYoung = true;
        }

        if (!isMale && isYoung && triggers >= earlyOnsetYoungFemaleTriggerThreshold) {
            return DiabetesRiskLevel.EARLY_ONSET;
        }

        if (isMale && isYoung && triggers >= earlyOnsetYoungMaleTriggerThreshold) {
            return DiabetesRiskLevel.EARLY_ONSET;
        }

        if (triggers >= inDangerOldTriggerThreshold) {
            return DiabetesRiskLevel.IN_DANGER;
        }

        if (!isMale && isYoung && triggers >= inDangerYoungFemaleTriggerThreshold) {
            return DiabetesRiskLevel.IN_DANGER;
        }

        if (isMale && isYoung && triggers >= inDangerYoungMaleTriggerThreshold) {
            return DiabetesRiskLevel.IN_DANGER;
        }

        if (!isYoung && triggers >= borderlineRiskTriggerThreshold) {
            return DiabetesRiskLevel.BORDERLINE;
        }

        return DiabetesRiskLevel.NONE;
    }

    private int calculateAge(LocalDate dob) {
        return (int) YEARS.between(dob, LocalDate.now());
    }

}
