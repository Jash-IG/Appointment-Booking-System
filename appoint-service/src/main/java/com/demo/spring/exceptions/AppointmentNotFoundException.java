package com.demo.spring.exceptions;

public class AppointmentNotFoundException extends AppointmentResourceException {

    public AppointmentNotFoundException(String message) {
        super(message);
    }

    public AppointmentNotFoundException() {
    }
}