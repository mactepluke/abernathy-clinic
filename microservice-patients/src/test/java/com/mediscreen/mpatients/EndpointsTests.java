package com.mediscreen.mpatients;

import com.mediscreen.mpatients.model.Patient;
import com.mediscreen.mpatients.repository.PatientRepository;
import com.mediscreen.mpatients.service.PatientService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
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
	private PatientService patientService;
	@MockBean
	private PatientRepository patientRepository;

	private Patient testpatient;

	@BeforeAll
	void setUp() {
		log.info("*** STARTING MICROSERVICE PATIENTS ENDPOINTS TESTS ***");

		testpatient = new Patient("Test", "Patient", LocalDate.now(), 'M', "test address", "06667778");
		testpatient.setPatientId(0);

	}

	@AfterAll
	void tearDown() {
		log.info("*** MICROSERVICE PATIENTS ENDPOINTS TESTS FINISHED ***");
	}


	@Test
	void addPatient() throws Exception {

		when(patientService.add(
				anyString(),
				anyString(),
				any(),
				anyChar(),
				anyString(),
				anyString()
		)).thenReturn(testpatient);

		when(patientRepository.findByFamilyAndGiven(anyString(), anyString())).thenReturn(null);
		when(patientRepository.save(any())).thenReturn(testpatient);

			mockMvc.perform(MockMvcRequestBuilders.post("/patient/add")
							.param("family", testpatient.getFamily())
							.param("given", testpatient.getGiven())
							.param("dob", testpatient.getDob().format(DateTimeFormatter.ISO_DATE))
				 			.param("sex", String.valueOf(testpatient.getSex()))
							.param("address", testpatient.getAddress())
							.param("phone", testpatient.getPhone()))
					.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void getPatient() throws Exception {
		when(patientService.find(
				anyString(),
				anyString())).thenReturn(testpatient);

		mockMvc.perform(MockMvcRequestBuilders.get("/patient/get")
						.param("family", testpatient.getFamily())
						.param("given", testpatient.getGiven()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void getAllPatients() throws Exception {
		when(patientService.findAll()).thenReturn(List.of(testpatient));

		mockMvc.perform(MockMvcRequestBuilders.get("/patient/getall"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void updatePatient() throws Exception {

		when(patientService.update(
				any(),
				any(),
				anyString(),
				anyString(),
				any(),
				anyChar(),
				anyString(),
				anyString()
		)).thenReturn(testpatient);

		when(patientRepository.findByFamilyAndGiven(anyString(), anyString())).thenReturn(testpatient);
		when(patientRepository.save(any())).thenReturn(testpatient);

		mockMvc.perform(MockMvcRequestBuilders.put("/patient/update")
						.param("family", testpatient.getFamily())
						.param("given", testpatient.getGiven())
						.param("newFamily", "newTest")
						.param("newGiven", "newPatient")
						.param("newDob", LocalDate.now().format(DateTimeFormatter.ISO_DATE))
						.param("newSex", "F")
						.param("newAddress", "new address")
						.param("newPhone", "999222334"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void deletePatient() throws Exception {
		when(patientService.delete(
				anyString(),
				anyString())).thenReturn(testpatient);

		mockMvc.perform(MockMvcRequestBuilders.delete("/patient/delete")
						.param("family", testpatient.getFamily())
						.param("given", testpatient.getGiven()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}


}
