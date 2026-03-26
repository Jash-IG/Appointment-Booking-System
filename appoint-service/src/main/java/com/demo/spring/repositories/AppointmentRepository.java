package com.demo.spring.repositories;

import com.demo.spring.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment,Long>{

}


