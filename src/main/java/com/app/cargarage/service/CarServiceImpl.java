package com.app.cargarage.service;

import com.app.cargarage.dto.ResponseDto;
import com.app.cargarage.model.Car;
import com.app.cargarage.model.CarDocument;
import com.app.cargarage.model.Part;
import com.app.cargarage.model.RepairOperations;
import com.app.cargarage.repository.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final CarDocumentRepository carDocumentRepository;
    private final RepairOperationsRepository operationsRepository;
    private final PartRepository partRepository;

    public CarServiceImpl(CarRepository carRepository, CustomerRepository customerRepository, CarDocumentRepository carDocumentRepository, RepairOperationsRepository operationsRepository, PartRepository partRepository) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.carDocumentRepository = carDocumentRepository;
        this.operationsRepository = operationsRepository;
        this.partRepository = partRepository;
    }

    @Override
    public ResponseDto uploadDocument(String licensePlate, MultipartFile document) {
        try {
            Optional<Car> car = carRepository.findCarByLicensePlate(licensePlate);
            if (car.isPresent()) {
                String fileName = StringUtils.cleanPath(document.getOriginalFilename());
                CarDocument carDocument = carDocumentRepository
                        .save(CarDocument.builder()
                                .documentType(document.getContentType())
                                .documentName("car_document-" + car.get().getLicensePlate() + "-" + fileName)
                                .document(document.getBytes())
                                .build());
                car.get().setCarDocument(carDocument);
                carRepository.save(car.get());

                return ResponseDto.builder()
                        .result(car.get())
                        .message("Document for this car has been added in the database")
                        .statusCode(HttpStatus.OK.value())
                        .build();
            } else {
                return ResponseDto.builder()
                        .result(null)
                        .message("There is no car against this license plate in the database")
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

    @Override
    public ResponseEntity<byte[]> getDocumentsByCarLicensePlate(String licensePlate) {
        try {
            Optional<Car> car = carRepository.findCarByLicensePlate(licensePlate);
            String documentName = car.get().getCarDocument().getDocumentName();

            if (car.isPresent()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/octet-stream"))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documentName + "\"")
                        .body(car.get().getCarDocument().getDocument());
            } else {
                throw new RuntimeException("There is no car against this license plate");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Some error occurred");
        }
    }

    @Override
    public ResponseDto addRepairingActionsInCar(String carLicensePlate, long repairingActionId) {
        try {
            Optional<Car> car = carRepository.findCarByLicensePlate(carLicensePlate);
            if (car.isPresent()) {
                Optional<RepairOperations> repairOperation = operationsRepository.findById(repairingActionId);
                if (repairOperation.isPresent()) {
                    car.get().getRepairOperationsList().add(repairOperation.get());
                    carRepository.saveAndFlush(car.get());
                    return ResponseDto.builder()
                            .result(car.get().getRepairOperationsList())
                            .message("The repairing action is added in the car repairing-list")
                            .statusCode(HttpStatus.OK.value())
                            .build();
                } else {
                    return ResponseDto.builder()
                            .result(null)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message("There is no repairing operation against this id")
                            .build();
                }
            } else {
                return ResponseDto.builder()
                        .result(null)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message("There is no car against this license plate")
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

    @Override
    public ResponseDto installPartsInCar(String carLicensePlate, long partId) {
        try {
            Optional<Car> car = carRepository.findCarByLicensePlate(carLicensePlate);
            if (car.isPresent()) {
                Optional<Part> part = partRepository.findById(partId);
                if (part.isPresent()) {
                    car.get().getPartsList().add(part.get());
                    carRepository.saveAndFlush(car.get());
                    return ResponseDto.builder()
                            .result(car.get().getRepairOperationsList())
                            .message("The part is successfully installed in the car !!")
                            .statusCode(HttpStatus.OK.value())
                            .build();
                } else {
                    return ResponseDto.builder()
                            .result(null)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message("There is no part in the database against this id")
                            .build();
                }
            } else {
                return ResponseDto.builder()
                        .result(null)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message("There is no car against this license plate")
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

    @Override
    public ResponseDto addCar(Car car) {
        try {
            car.setCustomer(customerRepository.save(car.getCustomer()));
            return ResponseDto.builder()
                    .result(carRepository.save(car))
                    .message("Car is successfully added in the database")
                    .statusCode(HttpStatus.OK.value())
                    .build();
        } catch (Exception e) {
            return ResponseDto.builder()
                    .result(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto listOfCars() {
        try {
            List<Car> carsList = carRepository.findAll();
            if (carsList.isEmpty()) {
                return ResponseDto.builder()
                        .result(carsList)
                        .message("There is no car registered yet in the database")
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build();
            } else {
                return ResponseDto.builder()
                        .result(carsList)
                        .message("This is the list of cars that are in the database")
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

    @Override
    public ResponseDto getCustomerByCarLicensePlate(String licensePlate) {

        try {
            Optional<Car> car = carRepository.findCarByLicensePlate(licensePlate);
            if (car.isPresent()) {
                return ResponseDto.builder()
                        .result(car.get().getCustomer())
                        .message("This is the owner of this car")
                        .statusCode(HttpStatus.OK.value())
                        .build();
            } else {
                return ResponseDto.builder()
                        .result(null)
                        .message("There is no car against this license plate")
                        .statusCode(HttpStatus.NOT_FOUND.value())
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
