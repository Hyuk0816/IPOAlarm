package com.sideprj.ipoAlarm.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EndDateSearchException extends RuntimeException{
    public EndDateSearchException(String message) {
        super(message);
    }
}
