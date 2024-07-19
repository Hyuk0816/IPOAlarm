package com.sideprj.ipoAlarm.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlarmAlreadyExistsException extends RuntimeException{

    public AlarmAlreadyExistsException(String message, String ipoName) {
        super(message+ipoName);
    }
}
