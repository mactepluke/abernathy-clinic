package com.mediscreen.mpatients.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CannotHandlePatientException extends RuntimeException {

    public CannotHandlePatientException(String message) {
        super(message);
    }
}
