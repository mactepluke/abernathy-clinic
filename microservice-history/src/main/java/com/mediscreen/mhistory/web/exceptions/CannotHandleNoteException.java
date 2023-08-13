package com.mediscreen.mhistory.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CannotHandleNoteException extends RuntimeException {

    public CannotHandleNoteException(String message) {
        super(message);
    }

}
