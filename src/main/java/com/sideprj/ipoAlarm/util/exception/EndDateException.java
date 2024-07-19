package com.sideprj.ipoAlarm.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EndDateException extends RuntimeException{
    public EndDateException(String message) {
        super(message);
    }
}
