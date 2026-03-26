package com.demo.spring.exceptions;

public class AppointmentResourceException extends RuntimeException {

    public AppointmentResourceException(String message) {
        super(message);
    }

    public AppointmentResourceException() {
    }
}