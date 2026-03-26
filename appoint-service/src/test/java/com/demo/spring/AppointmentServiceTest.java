package com.demo.spring;

import com.demo.spring.entity.Appointment;
import com.demo.spring.exceptions.AppointmentExistsException;
import com.demo.spring.exceptions.AppointmentNotFoundException;
import com.demo.spring.repositories.AppointmentRepository;
import com.demo.spring.services.AppointmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void testGetAllAppointmentsSuccess() {
        List<Appointment> list = Arrays.asList(
                new Appointment(1L, "A", "a@mail.com", LocalDate.now(), "10AM", "NEW", LocalDateTime.now()),
                new Appointment(2L, "B", "b@mail.com", LocalDate.now(), "11AM", "NEW", LocalDateTime.now())
        );

        when(appointmentRepository.findAll()).thenReturn(list);

        List<Appointment> result = appointmentService.getAllAppointments();

        Assertions.assertEquals(2, result.size());
    }


    @Test
    void testGetOneAppointmentSuccess() {
        Appointment appt =
                new Appointment(1L, "John", "john@mail.com", LocalDate.now(), "10AM", "NEW", LocalDateTime.now());

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appt));

        Appointment result = appointmentService.getOneAppointment(1L);

        Assertions.assertEquals("John", result.getName());
    }

    @Test
    void testGetOneAppointmentNotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                AppointmentNotFoundException.class,
                () -> appointmentService.getOneAppointment(1L)
        );
    }

    @Test
    void testSaveAppointmentSuccess() {
        Appointment appt =
                new Appointment(null, "Sam", "sam@mail.com", LocalDate.now(), "11AM", "NEW", LocalDateTime.now());

        when(appointmentRepository.save(appt)).thenReturn(appt);

        Appointment result = appointmentService.save(appt);

        Assertions.assertEquals("Sam", result.getName());
    }

    @Test
    void testSaveAppointmentAlreadyExists() {
        Appointment appt =
                new Appointment(1L, "Sam", "sam@mail.com", LocalDate.now(), "11AM", "NEW", LocalDateTime.now());

        when(appointmentRepository.existsById(1L)).thenReturn(true);

        Assertions.assertThrows(
                AppointmentExistsException.class,
                () -> appointmentService.save(appt)
        );

        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void testDeleteAppointmentSuccess() {
        when(appointmentRepository.existsById(1L)).thenReturn(true);

        String result = appointmentService.delete(1L);

        Assertions.assertEquals("appointment deleted", result);
        verify(appointmentRepository).deleteById(1L);
    }

    @Test
    void testDeleteAppointmentNotFound() {
        when(appointmentRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(
                AppointmentNotFoundException.class,
                () -> appointmentService.delete(1L)
        );

        verify(appointmentRepository, never()).deleteById(anyLong());
    }

    @Test
    void testUpdateAppointmentSuccess() {
        Appointment appt =
                new Appointment(1L, "Tom", "tom@mail.com", LocalDate.now(), "9AM", "NEW", LocalDateTime.now());

        when(appointmentRepository.existsById(1L)).thenReturn(true);
        when(appointmentRepository.save(appt)).thenReturn(appt);

        Appointment result = appointmentService.update(appt);

        Assertions.assertEquals("Tom", result.getName());
        verify(appointmentRepository).save(appt);
    }

    @Test
    void testUpdateAppointmentNotFound() {
        Appointment appt =
                new Appointment(1L, "Tom", "tom@mail.com", LocalDate.now(), "9AM", "NEW", LocalDateTime.now());

        when(appointmentRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(
                AppointmentNotFoundException.class,
                () -> appointmentService.update(appt)
        );

        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void testPartialUpdateSuccess() {
        Appointment existing =
                new Appointment(1L, "Old", "old@mail.com", LocalDate.now(), "9AM", "NEW", LocalDateTime.now());

        Appointment partial =
                new Appointment(null, null, null, null, null, null, null);
        partial.setName("Updated");
        partial.setStatus("CONFIRMED");

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(appointmentRepository.save(Mockito.any(Appointment.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Appointment result = appointmentService.partialUpdate(1L, partial);

        Assertions.assertEquals("Updated", result.getName());
        Assertions.assertEquals("CONFIRMED", result.getStatus());
        Assertions.assertEquals("old@mail.com", result.getEmail());

        verify(appointmentRepository).save(existing);
    }

    @Test
    void testPartialUpdateNotFound() {
        Appointment partial =
                new Appointment(null, null, null, null, null, null, null);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                AppointmentNotFoundException.class,
                () -> appointmentService.partialUpdate(1L, partial)
        );

        verify(appointmentRepository, never()).save(any());
    }
}
