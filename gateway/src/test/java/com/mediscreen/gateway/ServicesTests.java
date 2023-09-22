package com.mediscreen.gateway;

import com.mediscreen.gateway.model.User;
import com.mediscreen.gateway.repository.UserRepository;
import com.mediscreen.gateway.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Log4j2
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServicesTests {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User user;

    @BeforeAll
    void setUp() {
        log.info("*** STARTING GATEWAY SERVICES TESTS ***");

        user = new User();
        user.setUsername("unittestusername");
        user.setPassword("unittestPassword");
    }

    @AfterAll
    void tearDown() {
        log.info("*** USER GATEWAY SERVICES TESTS FINISHED ***");
    }

    @Test
    void contextLoads() {
    }

    @Test
    void findUser() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        assertEquals(userService.find(user.getUsername()), user);
    }

    @Test
    void createUserExisting() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.create(user.getUsername(), user.getPassword());

        assertNull(createdUser);
    }

    @Test
    void createUserNonExisting() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(user);

        User createdUser = userService.create(user.getUsername(), user.getPassword());

        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getPassword(), createdUser.getPassword());
    }

}
