package com.demo.spring.exceptions;

public class AppointmentExistsException extends AppointmentResourceException {

    public AppointmentExistsException(String message) {
        super(message);
    }

    public AppointmentExistsException() {
    }
}