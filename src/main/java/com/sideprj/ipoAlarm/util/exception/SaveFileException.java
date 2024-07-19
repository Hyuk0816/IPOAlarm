package com.sideprj.ipoAlarm.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SaveFileException extends RuntimeException {
    public SaveFileException(String message, Throwable stackTrace) {
        super(message, stackTrace);
    }
}
