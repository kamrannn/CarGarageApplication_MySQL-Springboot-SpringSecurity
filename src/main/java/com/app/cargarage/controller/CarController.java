package com.app.cargarage.controller;

import com.app.cargarage.dto.ResponseDto;
import com.app.cargarage.model.Car;
import com.app.cargarage.service.CarServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/car")
public class CarController {
    private final CarServiceImpl carService;

    public CarController(CarServiceImpl carService) {
        this.carService = carService;
    }

    @PostMapping("/add")
    public ResponseDto addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }

    @PostMapping("/uploadDocument")
    public ResponseDto uploadDocument(@RequestParam(name = "licensePlate") String licensePlate, @RequestParam(name = "document") MultipartFile document) {
        return carService.uploadDocument(licensePlate, document);
    }

    @PostMapping("/addRepairingActionsInCar")
    public ResponseDto addRepairingActionsInCar(@RequestParam(name = "licensePlate") String licensePlate, @RequestParam(name = "operation_id") long repairingOperationId) {
        return carService.addRepairingActionsInCar(licensePlate, repairingOperationId);
    }

    @PostMapping("/installPartsInCar")
    public ResponseDto installPartsInCar(@RequestParam(name = "licensePlate") String licensePlate, @RequestParam(name = "part_id") long partId) {
        return carService.installPartsInCar(licensePlate, partId);
    }

    @GetMapping("/list")
    public ResponseDto listOfCars() {
        return carService.listOfCars();
    }

    @GetMapping("/getCustomerByLicensePlate")
    public ResponseDto getCustomerByCarLicensePlate(@RequestParam(name = "licensePlate") String licensePlate) {
        return carService.getCustomerByCarLicensePlate(licensePlate);
    }

    @GetMapping("/document/getByLicensePlate/{licensePlate}")
    public ResponseEntity<byte[]> getDocumentsByCarLicensePlate(@PathVariable(name = "licensePlate") String licensePlate) {
        return carService.getDocumentsByCarLicensePlate(licensePlate);
    }
}
