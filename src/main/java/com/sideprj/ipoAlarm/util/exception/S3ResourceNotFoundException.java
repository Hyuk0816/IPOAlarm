package com.sideprj.ipoAlarm.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class S3ResourceNotFoundException extends RuntimeException{

    public S3ResourceNotFoundException(String bucket, String file) {
        super(file + " " + "not found in bucket " + " " + bucket);
    }
}
