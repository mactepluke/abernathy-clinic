package com.mediscreen.massessment;

import com.mediscreen.massessment.dto.NoteContentDTO;
import com.mediscreen.massessment.model.DiabetesRiskLevel;
import com.mediscreen.massessment.proxies.PatientHistoryApiProxy;
import com.mediscreen.massessment.service.AssessmentService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Log4j2
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class EndpointsTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssessmentService assessmentService;

    @MockBean
    private PatientHistoryApiProxy patientHistoryApiProxy;

    private NoteContentDTO noteContentDTO;

    @BeforeAll
    void setUp() {
        log.info("*** STARTING MICROSERVICE ASSESSMENT ENDPOINTS TESTS ***");

        noteContentDTO = new NoteContentDTO();
    }

    @AfterAll
    void tearDown() {
        log.info("*** MICROSERVICE ASSESSMENT ENDPOINTS TESTS FINISHED ***");
    }

    @Test
    void assessDiabetesRisk() throws Exception {
        when(assessmentService.assessDiabetesRisk(anyString(), anyChar(), any())).thenReturn(DiabetesRiskLevel.NONE);

        when(patientHistoryApiProxy.getFullHistory(
                anyString())).thenReturn(List.of(noteContentDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/assessment/assessDiabetesRisk")
                        .param("patientId", "0")
                        .param("sex", "M")
                        .param("dob", LocalDate.now().format(DateTimeFormatter.ISO_DATE)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
