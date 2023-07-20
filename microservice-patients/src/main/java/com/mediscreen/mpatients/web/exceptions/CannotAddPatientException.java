package com.mediscreen.mpatients.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CannotAddPatientException extends RuntimeException {

    public CannotAddPatientException(String message) {
        super(message);
    }
}
