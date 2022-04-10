package com.app.cargarage.controller;

import com.app.cargarage.dto.ResponseDto;
import com.app.cargarage.model.Car;
import com.app.cargarage.service.CarServiceImpl;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list")
    public ResponseDto listOfCars() {
        return carService.listOfCars();
    }

    @GetMapping("/getCustomerByLicensePlate")
    public ResponseDto getCustomerByCarLicensePlate(@RequestParam(name = "licensePlate") String licensePlate) {
        return carService.getCustomerByCarLicensePlate(licensePlate);
    }
}
