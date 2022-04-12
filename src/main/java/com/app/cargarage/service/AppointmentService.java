package com.app.cargarage.service;

import com.app.cargarage.dto.ResponseDto;
import com.app.cargarage.model.Appointment;

public interface AppointmentService {
    ResponseDto create(Appointment appointment);
}
