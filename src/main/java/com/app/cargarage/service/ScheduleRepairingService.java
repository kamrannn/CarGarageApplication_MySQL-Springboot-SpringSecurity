package com.app.cargarage.service;

import com.app.cargarage.dto.ResponseDto;
import com.app.cargarage.model.ScheduleRepairing;

public interface ScheduleRepairingService {
    public ResponseDto createRepairSchedule(String licensePlate, ScheduleRepairing scheduleRepairing);
}
