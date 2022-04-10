package com.app.cargarage.service;

import com.app.cargarage.dto.ResponseDto;
import com.app.cargarage.model.Car;
import com.app.cargarage.model.ScheduleRepairing;
import com.app.cargarage.repository.CarRepository;
import com.app.cargarage.repository.ScheduleRepairingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduleRepairingServiceImpl implements ScheduleRepairingService {

    private final ScheduleRepairingRepository repairingRepository;
    private final CarRepository carRepository;

    public ScheduleRepairingServiceImpl(ScheduleRepairingRepository repairingRepository, CarRepository carRepository) {
        this.repairingRepository = repairingRepository;
        this.carRepository = carRepository;
    }

    @Override
    public ResponseDto createRepairSchedule(String licensePlate, ScheduleRepairing scheduleRepairing) {
        try {
            Optional<Car> car = carRepository.findCarByLicensePlate(licensePlate);
            if (car.isPresent()) {
                scheduleRepairing.setCar(car.get());
                return ResponseDto.builder()
                        .result(repairingRepository.save(scheduleRepairing))
                        .message("The repairing service is successfully scheduled against this car with license plate: " + car.get().getLicensePlate())
                        .statusCode(HttpStatus.OK.value())
                        .build();
            } else {
                return ResponseDto.builder()
                        .result(null)
                        .message("There is no car against this license plate")
                        .statusCode(HttpStatus.OK.value())
                        .build();
            }

        } catch (Exception e) {
            return ResponseDto.builder()
                    .result(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message(e.getMessage())
                    .build();
        }
    }
}
