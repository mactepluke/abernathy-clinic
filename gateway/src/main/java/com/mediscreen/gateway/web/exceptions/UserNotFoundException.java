package com.mediscreen.gateway.web.exceptions;

import com.mediscreen.gateway.web.controller.UserController;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message)    {
        super(message);
        log.error(message);
    }
}
