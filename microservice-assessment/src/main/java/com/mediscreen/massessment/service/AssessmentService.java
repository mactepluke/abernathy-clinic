package com.mediscreen.massessment.service;

import com.mediscreen.massessment.dto.NoteContentDTO;
import com.mediscreen.massessment.model.RiskLevel;
import com.mediscreen.massessment.proxies.PatientHistoryApiProxy;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.time.temporal.ChronoUnit.YEARS;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Service
public class AssessmentService {
    @Value("#{'${triggering_terms}'.split(',')}")
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

    //private final List<String> streamlinedTerms;

    public AssessmentService() {
        //this.streamlinedTerms = new ArrayList<>();
    }

    @Autowired
    PatientHistoryApiProxy patientHistoryApiProxy;

    public RiskLevel assessDiabetesRisk(String patientId, char sex, LocalDate dob)  {

        RiskLevel riskLevel;
        int triggers;

        log.debug(this.triggeringTerms);

        /*if (this.triggeringTerms != null) {
            this.triggeringTerms.forEach(term -> this.streamlinedTerms.add(StringUtils.stripAccents(term)));
            log.debug(this.streamlinedTerms);
        }*/

        List<NoteContentDTO> noteContentsDTO = this.patientHistoryApiProxy.getFullHistory(patientId);

        if (noteContentsDTO == null || this.triggeringTerms.isEmpty()) {
            return RiskLevel.UNKNOWN;
        }

        triggers = countTriggersInNoteContentList(noteContentsDTO);

        riskLevel = calculateRisk(triggers, sex, dob);

        return riskLevel;
    }

    private int countTriggersInNoteContentList(@NotNull List<NoteContentDTO> noteContentsDTO) {

        AtomicInteger triggers = new AtomicInteger();

        if (noteContentsDTO.isEmpty()) {
            return 0;
        }

        noteContentsDTO.forEach( noteContent ->
                triggers.set(triggers.get() + countTriggersInNoteContent(StringUtils.stripAccents(noteContent.getContent()))));

        return triggers.get();
    }

    private int countTriggersInNoteContent(String content) {

        AtomicInteger triggers = new AtomicInteger();

        triggeringTerms.forEach(triggeringTerm -> {
            if (containsIgnoreCase(triggeringTerm, content))    {
                triggers.getAndIncrement();
            }
        });
        return triggers.get();
    }

    private RiskLevel calculateRisk(int triggers, char sex, LocalDate dob) {

        boolean isMale;
        boolean isYoung = false;

        if (triggers < borderlineRiskTriggerThreshold)  {
            return RiskLevel.NONE;
        }

        if (triggers >= earlyOnsetOldTriggerThreshold)   {
            return RiskLevel.EARLY_ONSET;
        }

        switch (sex)    {
            case 'M' -> isMale = true;
            case 'F' -> isMale = false;
            default -> {
                return RiskLevel.UNKNOWN;
            }
        }

        int age = calculateAge(dob);

        if (age < 0) {
            return RiskLevel.UNKNOWN;
        }

        if (age < riskAgeThreshold)    {
            isYoung = true;
        }

        if (!isMale && isYoung && triggers >= earlyOnsetYoungFemaleTriggerThreshold)    {
            return RiskLevel.EARLY_ONSET;
        }

        if (isMale && isYoung && triggers >= earlyOnsetYoungMaleTriggerThreshold)    {
            return RiskLevel.EARLY_ONSET;
        }

        if (triggers >= inDangerOldTriggerThreshold)   {
            return RiskLevel.IN_DANGER;
        }

        if (!isMale && isYoung && triggers >= inDangerYoungFemaleTriggerThreshold)    {
            return RiskLevel.IN_DANGER;
        }

        if (isMale && isYoung && triggers >= inDangerYoungMaleTriggerThreshold)    {
            return RiskLevel.IN_DANGER;
        }

        if (!isYoung && triggers >= borderlineRiskTriggerThreshold) {
            return RiskLevel.BORDERLINE;
        }

        return RiskLevel.UNKNOWN;
    }

    private int calculateAge(LocalDate dob) {
        return (int) YEARS.between(dob, LocalDate.now());
    }

}
