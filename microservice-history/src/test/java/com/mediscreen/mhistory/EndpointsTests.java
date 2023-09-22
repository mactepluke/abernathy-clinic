package com.mediscreen.mhistory;

import com.mediscreen.mhistory.model.LightNote;
import com.mediscreen.mhistory.model.Note;
import com.mediscreen.mhistory.repository.HistoryRepository;
import com.mediscreen.mhistory.service.HistoryService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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
    private HistoryService historyService;
    @MockBean
    private HistoryRepository historyRepository;

    private Note testNote;
    private LightNote lightTestNote;
    private String patientId;

    @BeforeAll
    void setUp() {
        log.info("*** STARTING MICROSERVICE HISTORY ENDPOINTS TESTS ***");

        patientId = "testId";
        testNote = new Note(patientId, "testTitle", LocalDateTime.now(), "testContent");
        testNote.setId("0");

    }

    @AfterAll
    void tearDown() {
        log.info("*** MICROSERVICE HISTORY ENDPOINTS TESTS FINISHED ***");
    }

    @Test
    void addNote() throws Exception {

        when(historyRepository.insert(any(Note.class))).thenReturn(testNote);

        mockMvc.perform(MockMvcRequestBuilders.post("/history/addNote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"patientId\":" + "\"" + testNote.getPatientId() +
                                "\"" + ",\"title\":" + "\"" + testNote.getTitle() +
                                "\"" + ",\"content\":" + "\"" + testNote.getContent() + "\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void getNote() throws Exception {
        when(historyService.findById(testNote.getId())).thenReturn(testNote);

        when(historyRepository.findById(
                anyString())).thenReturn(Optional.ofNullable(testNote));

        mockMvc.perform(MockMvcRequestBuilders.get("/history/getNote")
                        .param("id", testNote.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getHistory() throws Exception {
        when(historyService.findAllNotes(patientId)).thenReturn(List.of(lightTestNote));

        mockMvc.perform(MockMvcRequestBuilders.get("/history/getHistory"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getFullHistory() throws Exception {
        when(historyService.findAllNotesWithContent(patientId)).thenReturn(List.of(testNote));

        mockMvc.perform(MockMvcRequestBuilders.get("/history/getFullHistory"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updatePatient() throws Exception {

        when(historyService.update(
                anyString(),
                anyString(),
                anyString()
        )).thenReturn(testNote);

        when(historyRepository.findById(any())).thenReturn(Optional.ofNullable(testNote));
        when(historyRepository.save(any())).thenReturn(testNote);

        mockMvc.perform(MockMvcRequestBuilders.put("/history/updateNote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":" + "\"" + testNote.getId() +
                                "\"" + ",\"title\":" + "\"" + testNote.getTitle() +
                                "\"" + ",\"content\":" + "\"" + testNote.getContent() + "\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteNote() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/history/deleteNote")
                        .param("id", testNote.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteAllNote() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/history/deleteAllNotes")
                        .param("patientId", testNote.getPatientId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
