package com.demo.spring;

import com.demo.spring.controllers.AppointmentController;
import com.demo.spring.entity.Appointment;
import com.demo.spring.services.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
class AppointmentControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private AppointmentService appointmentService;

    @Test
    void testGetAllAppointmentsSuccess() throws Exception {

        List<Appointment> list = List.of(
                new Appointment(1L, "A", "a@mail.com",
                        LocalDate.now(), "10AM", "NEW", LocalDateTime.now()),
                new Appointment(2L, "B", "b@mail.com",
                        LocalDate.now(), "11AM", "NEW", LocalDateTime.now())
        );

        when(appointmentService.getAllAppointments()).thenReturn(list);

        mvc.perform(get("/appointments/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[1].name").value("B"));
    }

    @Test
    void testGetOneAppointmentSuccess() throws Exception {

        Appointment appt = new Appointment(10L, "John", "john@mail.com",
                LocalDate.now(), "10AM", "NEW", LocalDateTime.now());

        when(appointmentService.getOneAppointment(10L)).thenReturn(appt);

        mvc.perform(get("/appointments/10").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testCreateAppointmentSuccess() throws Exception {

        Appointment saved = new Appointment(100L, "Sam", "sam@mail.com",
                LocalDate.now(), "11AM", "NEW", LocalDateTime.now());

        when(appointmentService.save(any(Appointment.class))).thenReturn(saved);

        String body = """
                {
                  "name": "Sam",
                  "email": "sam@mail.com",
                  "appointmentDate": "2026-03-26",
                  "timeSlot": "11AM",
                  "status": "NEW",
                  "createdAt": "2026-03-26T10:00:00"
                }
                """;

        mvc.perform(post("/appointments/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.name").value("Sam"));
    }

    @Test
    void testUpdateAppointmentSuccess() throws Exception {

        Appointment updated = new Appointment(5L, "Tom", "tom@mail.com",
                LocalDate.now(), "9AM", "UPDATED", LocalDateTime.now());

        when(appointmentService.update(any(Appointment.class))).thenReturn(updated);

        String body = """
                {
                  "id": 5,
                  "name": "Tom",
                  "email": "tom@mail.com",
                  "appointmentDate": "2026-03-26",
                  "timeSlot": "9AM",
                  "status": "UPDATED",
                  "createdAt": "2026-03-26T10:00:00"
                }
                """;

        mvc.perform(put("/appointments/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.status").value("UPDATED"));
    }

    @Test
    void testPartialUpdateAppointmentSuccess() throws Exception {

        Appointment updated = new Appointment(7L, "Updated", "old@mail.com",
                LocalDate.now(), "9AM", "CONFIRMED", LocalDateTime.now());

        when(appointmentService.partialUpdate(eq(7L), any(Appointment.class))).thenReturn(updated);

        String body = """
                {
                  "name": "Updated",
                  "status": "CONFIRMED"
                }
                """;

        mvc.perform(patch("/appointments/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.name").value("Updated"))
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    void testDeleteAppointmentSuccess() throws Exception {

        when(appointmentService.delete(9L)).thenReturn("appointment deleted");

        mvc.perform(delete("/appointments/9"))
                .andExpect(status().isOk())
                .andExpect(content().string("appointment deleted"));
    }
}

