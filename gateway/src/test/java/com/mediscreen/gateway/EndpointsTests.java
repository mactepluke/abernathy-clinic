package com.mediscreen.gateway;

import com.mediscreen.gateway.model.AuthRequest;
import com.mediscreen.gateway.service.UserService;
import com.mediscreen.gateway.web.controller.UserController;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@Log4j2
@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = ConfigTests.class)
class EndpointsTests {

    @Autowired
    private WebTestClient webTestClient;

    private AuthRequest authRequest;

    @BeforeAll
    void setUp() {
        log.info("*** STARTING GATEWAY ENDPOINTS TESTS ***");

        authRequest = new AuthRequest();

        authRequest.setUsername("unittestUsername");
        authRequest.setPassword("unittestPassword");
    }

    @AfterAll
    void tearDown() {
        log.info("*** GATEWAY ENDPOINTS TESTS FINISHED ***");
    }


    @Test
    void createUser() {

        webTestClient.post()
                .uri("http://localhost:8080/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("{\"username\":\"unittestusername\",\"password\":\"azeAZE@12\"}"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void loginWithUser()   {

        webTestClient.post()
                .uri("http://localhost:8080/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("{\"username\":\"testusername\",\"password\":\"azeAZE@12\"}"))
                .exchange()
                .expectStatus().isOk();
    }

}
