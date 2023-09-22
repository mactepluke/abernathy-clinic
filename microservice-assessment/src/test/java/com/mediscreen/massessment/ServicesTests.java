package com.mediscreen.massessment;

import com.mediscreen.massessment.dto.NoteContentDTO;
import com.mediscreen.massessment.model.DiabetesRiskLevel;
import com.mediscreen.massessment.proxies.PatientHistoryApiProxy;
import com.mediscreen.massessment.service.AssessmentService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Log4j2
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServicesTests {

    @Autowired
    private AssessmentService assessmentService;

    @MockBean
    private PatientHistoryApiProxy patientHistoryApiProxy;

    private List<NoteContentDTO> noteContentsDTO;


    @BeforeAll
    void setUp() {
        log.info("*** STARTING MICROSERVICE ASSESSMENT SERVICES TESTS ***");
    }

    @BeforeEach
    void initializeNoteContents() {
        noteContentsDTO = new ArrayList<>();
    }

    @AfterEach
    void clearNoteContents()    {
        noteContentsDTO = null;
    }

    @AfterAll
    void tearDown() {
        log.info("*** MICROSERVICE ASSESSMENT SERVICES TESTS FINISHED ***");
    }

    @Test
    void assessRiskIsNone() {

        char sex = 'F';
        LocalDate dob = LocalDate.of(1966,12,12);

        NoteContentDTO noteContentDTO = new NoteContentDTO();

        noteContentDTO.setContent("""
                Le patient déclare qu'il 'se sent très bien'
                Poids égal ou inférieur au poids recommandé""");
        noteContentsDTO.add(noteContentDTO);

        when(patientHistoryApiProxy.getFullHistory(anyString())).thenReturn(noteContentsDTO);

        DiabetesRiskLevel assessment = assessmentService.assessDiabetesRisk("0", sex, dob);

        assertEquals(DiabetesRiskLevel.NONE, assessment);
    }

    @Test
    void assessRiskIsBorderline() {

        char sex = 'M';
        LocalDate dob = LocalDate.of(1945,6,24);

        NoteContentDTO noteContentDTO = new NoteContentDTO();

        noteContentDTO.setContent("""
                Le patient déclare qu'il ressent beaucoup de stress au travail
                Il se plaint également que son audition est anormale dernièrement""");
        noteContentsDTO.add(noteContentDTO);
        noteContentDTO = new NoteContentDTO();

        noteContentDTO.setContent("""
                Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois
                Il remarque également que son audition continue d'être anormale""");
        noteContentsDTO.add(noteContentDTO);

        when(patientHistoryApiProxy.getFullHistory(anyString())).thenReturn(noteContentsDTO);

        DiabetesRiskLevel assessment = assessmentService.assessDiabetesRisk("0", sex, dob);

        assertEquals(DiabetesRiskLevel.BORDERLINE, assessment);
    }

    @Test
    void assessRiskIsInDanger() {

        char sex = 'M';
        LocalDate dob = LocalDate.of(2004,6,18);

        NoteContentDTO noteContentDTO = new NoteContentDTO();

        noteContentDTO.setContent("Le patient déclare qu'il fume depuis peu");
        noteContentsDTO.add(noteContentDTO);
        noteContentDTO = new NoteContentDTO();

        noteContentDTO.setContent("""
                                     Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière
                                     Il se plaint également de crises d’apnée respiratoire anormales
                                     Tests de laboratoire indiquant un taux de cholestérol LDL élevé""");
        noteContentsDTO.add(noteContentDTO);

        log.debug(noteContentsDTO.toString());

        when(patientHistoryApiProxy.getFullHistory(anyString())).thenReturn(noteContentsDTO);

        DiabetesRiskLevel assessment = assessmentService.assessDiabetesRisk("0", sex, dob);

        assertEquals(DiabetesRiskLevel.IN_DANGER, assessment);
    }

    @Test
    void assessRiskIsEarlyOnset() {

        char sex = 'F';
        LocalDate dob = LocalDate.of(2002,6,28);

        NoteContentDTO noteContentDTO = new NoteContentDTO();

        noteContentDTO.setContent("""
                Le patient déclare qu'il lui est devenu difficile de monter les escaliers
                Il se plaint également d’être essoufflé
                Tests de laboratoire indiquant que les anticorps sont élevés
                Réaction aux médicaments""");
        noteContentsDTO.add(noteContentDTO);
        noteContentDTO = new NoteContentDTO();

        noteContentDTO.setContent("Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps");
        noteContentsDTO.add(noteContentDTO);
        noteContentDTO = new NoteContentDTO();

        noteContentDTO.setContent("""
                Le patient déclare avoir commencé à fumer depuis peu
                Hémoglobine A1C supérieure au niveau recommandé""");
        noteContentsDTO.add(noteContentDTO);
        noteContentDTO = new NoteContentDTO();

        noteContentDTO.setContent("Taille, Poids, Cholestérol, Vertige et Réaction");
        noteContentsDTO.add(noteContentDTO);

        when(patientHistoryApiProxy.getFullHistory(anyString())).thenReturn(noteContentsDTO);

        DiabetesRiskLevel assessment = assessmentService.assessDiabetesRisk("0", sex, dob);

        assertEquals(DiabetesRiskLevel.EARLY_ONSET, assessment);
    }

}
