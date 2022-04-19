package com.app.cargarage.service;

import com.app.cargarage.dto.ResponseDto;
import com.app.cargarage.model.Car;
import com.app.cargarage.model.Part;
import com.app.cargarage.model.Receipt;
import com.app.cargarage.model.RepairOperations;
import com.app.cargarage.repository.CarRepository;
import com.app.cargarage.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    @Autowired
    ReceiptRepository receiptRepository;
    @Autowired
    CarRepository carRepository;

    @Override
    public ResponseDto generateReceipt(String carLicensePlate) {
        try {
            Optional<Car> car = carRepository.findCarByLicensePlate(carLicensePlate);
            Receipt receipt = new Receipt();
            receipt.setCarLicensePlate(carLicensePlate);
            receipt.setInspectionAmount(45.0);
            receipt.setStatus("pending");

            if (car.isPresent()) { //when car is present
                //Here we are going to calculate the total amount for the repair operations.
                double totalRepairingOperationsAmount = 0;
                for (RepairOperations operation : car.get().getRepairOperationsList()
                ) {
                    receipt.getRepairOperationsList().add(operation);
                    totalRepairingOperationsAmount = operation.getPrice() + totalRepairingOperationsAmount;
                }
                receipt.setRepairOperationsAmount(totalRepairingOperationsAmount);

                double totalPartsInstallationAmount = 0;
                for (Part part : car.get().getPartsList()
                ) {
                    receipt.getPartsList().add(part);
                    totalPartsInstallationAmount = part.getPrice() + totalPartsInstallationAmount;
                }
                receipt.setPartsAmount(totalPartsInstallationAmount);
                return ResponseDto.builder()
                        .result(receiptRepository.save(receipt))
                        .statusCode(HttpStatus.OK.value())
                        .message("The receipt has been generated against car having licence plate: " + carLicensePlate)
                        .build();
            } else { //when car is not present
                return ResponseDto.builder()
                        .result(null)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message("There is not car against licence plate: " + carLicensePlate)
                        .build();
            }
        } catch (Exception e) { //When an exception will come
            return ResponseDto.builder()
                    .result(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto listReceipts() {
        try {
            List<Receipt> receipts = receiptRepository.findAll();
            if (receipts.isEmpty()) {
                return ResponseDto.builder()
                        .result(receipts)
                        .statusCode(HttpStatus.OK.value())
                        .message("There is no receipt generated yet in the database")
                        .build();
            } else {
                return ResponseDto.builder()
                        .result(receipts)
                        .statusCode(HttpStatus.OK.value())
                        .message("This is the list of generated receipts in the database")
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
    public ResponseDto getReceiptsByLicensePlate(String licensePlate) {
        try {
            List<Receipt> receipts = receiptRepository.findAllByCarLicensePlate(licensePlate);
            if (receipts.isEmpty()) {
                return ResponseDto.builder()
                        .result(receipts)
                        .statusCode(HttpStatus.OK.value())
                        .message("There is no receipt generated yet in the database")
                        .build();
            } else {
                return ResponseDto.builder()
                        .result(receipts)
                        .statusCode(HttpStatus.OK.value())
                        .message("This is the list of generated receipts in the database")
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
