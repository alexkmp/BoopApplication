package com.boop.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class BoopApplicationExceptionHandler {

    @ExceptionHandler(BoopNotFoundException.class)
    ResponseEntity<HttpErrorInfo> handleNotFoundExceptions(BoopNotFoundException exception)  {
        return createHttpErrorInfo(HttpStatus.NOT_FOUND, exception);
    }

    private ResponseEntity<HttpErrorInfo> createHttpErrorInfo(HttpStatus httpStatus, Exception exception) {
        String message = exception.getMessage();
        return new ResponseEntity(new HttpErrorInfo(httpStatus, message), httpStatus);
    }
}
