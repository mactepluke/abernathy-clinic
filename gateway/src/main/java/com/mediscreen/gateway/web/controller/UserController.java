package com.mediscreen.gateway.web.controller;

import com.mediscreen.gateway.configuration.JWTUtil;
import com.mediscreen.gateway.model.AuthRequest;
import com.mediscreen.gateway.model.AuthResponse;
import com.mediscreen.gateway.model.User;
import com.mediscreen.gateway.service.UserService;
import com.mediscreen.gateway.web.exceptions.CannotHandleUserException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;


    @PostMapping(value = "/create")
    public ResponseEntity<User> create(@Valid @RequestBody AuthRequest authRequest) {
        User user;

        log.info("Create user request received with params: username='{}', password=******", authRequest.getUsername());

        user = userService.create(authRequest.getUsername(), authRequest.getPassword());

        if (user == null) throw new CannotHandleUserException("Cannot create user.");
        log.info("User created with username='{}'.", authRequest.getUsername());

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody @Valid AuthRequest authRequest)   {
        return userService.findByUsername(authRequest.getUsername())
                .filter(user -> passwordEncoder.matches(authRequest.getPassword(),user.getPassword()))
                .map(user -> ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(user))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
