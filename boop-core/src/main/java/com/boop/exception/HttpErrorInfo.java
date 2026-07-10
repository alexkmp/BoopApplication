package com.boop.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class HttpErrorInfo {

    private ZonedDateTime timestamp;
    private HttpStatus httpStatus;
    private String message;

    public HttpErrorInfo() {
    }

    public HttpErrorInfo(HttpStatus httpStatus, String message) {
        this.timestamp = ZonedDateTime.now();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getStatus() {
        return httpStatus.value();
    }

    public String getError() {
        return httpStatus.getReasonPhrase();
    }
}
