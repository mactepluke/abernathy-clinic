package com.mediscreen.gateway.web.controller;

import com.mediscreen.gateway.model.User;
import com.mediscreen.gateway.service.UserService;
import com.mediscreen.gateway.web.exceptions.CannotHandleUserException;
import com.mediscreen.gateway.web.exceptions.InvalidCredentialsException;
import com.mediscreen.gateway.web.exceptions.UserNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/create")
    public ResponseEntity<User> create(
            @RequestParam @NotBlank @Size(min = 3, max = 50) String username,
            @RequestParam @NotBlank @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&]).{8,30}$",
                    message = "password must be 8-30 chars, at least 1 lower case, 1 upper case and 1 special char")
            @Size(min = 8, max = 30) String password
    )    {
        User user;

        log.info("Create user request received with params: username='{}', password=******", username);

        user = userService.create(username, password);

        if (user == null) throw new CannotHandleUserException("Cannot create user.");
        log.info("User created with username='{}'.", user.getUsername());

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping(value = "/login")
    public ResponseEntity<User> login(
            @RequestParam @NotBlank String username,
            @RequestParam @NotBlank String password
    )   {
        User user;

        log.info("Login request received with params: username='{}', password=******", username);

        user = userService.find(username);

        if (user == null) throw new UserNotFoundException("User does not exist.");

        if (!userService.passwordIsValid(user, password)) throw new InvalidCredentialsException("Incorrect password.");

        log.info("User logged in with username '{}'.", user.getUsername());

        user.setPassword(password);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
