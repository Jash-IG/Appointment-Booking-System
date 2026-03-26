package com.demo.spring.controllers;
import com.demo.spring.entity.Appointment;
import com.demo.spring.services.AppointmentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController


@RequestMapping("/appointments")
@CrossOrigin(origins = "http://localhost:8087")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping(path="/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appointment> getOneAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getOneAppointment(id));
    }

    @PostMapping(path="/",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        return ResponseEntity.ok(appointmentService.save(appointment));
    }

    @PutMapping(path="/",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appointment> updateAppointment(@RequestBody Appointment appointment) {
        return ResponseEntity.ok(appointmentService.update(appointment));
    }

    @PatchMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appointment> partialUpdateAppointment(
            @PathVariable Long id,
            @RequestBody Appointment appointment) {

        return ResponseEntity.ok(appointmentService.partialUpdate(id, appointment));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.delete(id));
    }
}
