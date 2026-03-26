package com.demo.spring.exceptions;

import com.demo.spring.util.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppointmentExceptionHandler {

    @ExceptionHandler(AppointmentResourceException.class)
    public ResponseEntity<ResponseMessage> handle(RuntimeException e) {
        return ResponseEntity
                .status(409)
                .body(new ResponseMessage(e.getMessage()));
    }
}