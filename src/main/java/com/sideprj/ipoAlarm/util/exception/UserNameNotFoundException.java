package com.sideprj.ipoAlarm.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNameNotFoundException extends RuntimeException{

    private String statusCode;

    public UserNameNotFoundException(String message, String statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
