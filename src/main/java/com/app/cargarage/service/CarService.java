package com.app.cargarage.service;

import com.app.cargarage.dto.ResponseDto;
import com.app.cargarage.model.Car;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CarService {
    ResponseDto addCar(Car car);

    ResponseDto listOfCars();

    ResponseDto getCustomerByCarLicensePlate(String licenseNumber);

    ResponseDto uploadDocument(String licensePlate, MultipartFile document);

    ResponseEntity<byte[]> getDocumentsByCarLicensePlate(String licensePlate);
}
