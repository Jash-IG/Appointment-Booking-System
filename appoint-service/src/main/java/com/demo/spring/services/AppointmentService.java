package com.demo.spring.services;

import com.demo.spring.entity.Appointment;
import com.demo.spring.exceptions.AppointmentExistsException;
import com.demo.spring.exceptions.AppointmentNotFoundException;
import com.demo.spring.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getOneAppointment(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new AppointmentNotFoundException("Appointment with id " + id + " not found"));
    }

    public Appointment save(Appointment appointment) {
        if (appointment.getId() != null &&
                appointmentRepository.existsById(appointment.getId())) {
            throw new AppointmentExistsException("Appointment already exists");
        }
        return appointmentRepository.save(appointment);
    }

    public String delete(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return "appointment deleted";
        }
        throw new AppointmentNotFoundException("Appointment not found");
    }

    public Appointment update(Appointment appointment) {
        if (appointment.getId() == null ||
                !appointmentRepository.existsById(appointment.getId())) {
            throw new AppointmentNotFoundException("Appointment not found");
        }
        return appointmentRepository.save(appointment);
    }

    public Appointment partialUpdate(Long id, Appointment partialAppointment) {

        Appointment target = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new AppointmentNotFoundException("Appointment not found"));

        if (partialAppointment.getName() != null) {
            target.setName(partialAppointment.getName());
        }
        if (partialAppointment.getEmail() != null) {
            target.setEmail(partialAppointment.getEmail());
        }
        if (partialAppointment.getAppointmentDate() != null) {
            target.setAppointmentDate(partialAppointment.getAppointmentDate());
        }
        if (partialAppointment.getTimeSlot() != null) {
            target.setTimeSlot(partialAppointment.getTimeSlot());
        }
        if (partialAppointment.getStatus() != null) {
            target.setStatus(partialAppointment.getStatus());
        }

        return appointmentRepository.save(target);
    }
}