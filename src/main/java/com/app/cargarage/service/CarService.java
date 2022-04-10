package com.app.cargarage.service;

import com.app.cargarage.dto.ResponseDto;
import com.app.cargarage.model.Car;

public interface CarService {
    ResponseDto addCar(Car car);
    ResponseDto listOfCars();
    ResponseDto getCustomerByCarLicensePlate(String licenseNumber);
}
