package com.mediscreen.mpatients.web.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CannotHandlePatientException extends RuntimeException {

    public CannotHandlePatientException(String message) {
        super(message);
        log.error(message);
    }
}
