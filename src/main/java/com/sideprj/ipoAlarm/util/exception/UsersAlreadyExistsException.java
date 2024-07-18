package com.sideprj.ipoAlarm.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UsersAlreadyExistsException extends RuntimeException {

    public UsersAlreadyExistsException(String message) {
        super(message);
    }

}
