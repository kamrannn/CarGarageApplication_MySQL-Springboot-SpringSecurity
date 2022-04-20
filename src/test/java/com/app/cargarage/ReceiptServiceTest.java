package com.app.cargarage;

import com.app.cargarage.model.*;
import com.app.cargarage.repository.CarRepository;
import com.app.cargarage.repository.PartRepository;
import com.app.cargarage.repository.ReceiptRepository;
import com.app.cargarage.service.PartServiceImpl;
import com.app.cargarage.service.ReceiptServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class ReceiptServiceTest {

    @Autowired
    ReceiptServiceImpl receiptService;
    @MockBean
    ReceiptRepository receiptRepository;
    @MockBean
    CarRepository carRepository;

    @Test
    void generateReceiptTest() {
        Customer customer = Customer.builder()
                .id(1L)
                .phoneNumber("+923125153352")
                .address("Gul berg Greens, Islamabad")
                .surname("Kamran Abbasi")
                .build();

        Car car = Car.builder()
                .id(1)
                .customer(customer)
                .licensePlate("LEJ 2091")
                .repairStatus("pending")
                .build();

        RepairOperations repairOperation = RepairOperations.builder()
                .id(1)
                .repairAction("To change the brakes")
                .price(5000)
                .build();

        car.setRepairOperationsList(Collections.singletonList(repairOperation));

        Part part = Part.builder()
                .id(1)
                .name("Brake")
                .price(500)
                .stock(10)
                .build();
        car.setPartsList(Collections.singletonList(part));
        when(carRepository.findCarByLicensePlate(car.getLicensePlate())).thenReturn(Optional.of(car));
        assertEquals(200, receiptService.generateReceipt("LEJ 2091").getStatusCode());
    }

    @Test
    void listOfReceiptsTest() {
        Customer customer = Customer.builder()
                .id(1L)
                .phoneNumber("+923125153352")
                .address("Gul berg Greens, Islamabad")
                .surname("Kamran Abbasi")
                .build();

        Car car = Car.builder()
                .id(1)
                .customer(customer)
                .licensePlate("LEJ 2091")
                .repairStatus("pending")
                .build();

        RepairOperations repairOperation = RepairOperations.builder()
                .id(1)
                .repairAction("To change the brakes")
                .price(5000)
                .build();

        car.setRepairOperationsList(Collections.singletonList(repairOperation));

        Part part = Part.builder()
                .id(1)
                .name("Brake")
                .price(500)
                .stock(10)
                .build();
        car.setPartsList(Collections.singletonList(part));
        Receipt receipt = Receipt.builder()
                .id(1)
                .carLicensePlate("LEJ 2091")
                .inspectionAmount(100)
                .status("pending")
                .partsList(car.getPartsList())
                .repairOperationsList(car.getRepairOperationsList())
                .repairOperationsAmount(200)
                .partsAmount(300)
                .totalAmountOfRepairing(726)
                .build();

        List<Receipt> receiptsList = new ArrayList<>(Collections.singletonList(receipt));

        when(receiptRepository.findAll()).thenReturn(receiptsList);
        assertEquals(receiptsList, receiptService.listReceipts().getResult());
    }

    @Test
    void getReceiptsByLicensePlate() {
        Customer customer = Customer.builder()
                .id(1L)
                .phoneNumber("+923125153352")
                .address("Gul berg Greens, Islamabad")
                .surname("Kamran Abbasi")
                .build();

        Car car = Car.builder()
                .id(1)
                .customer(customer)
                .licensePlate("LEJ 2091")
                .repairStatus("pending")
                .build();

        RepairOperations repairOperation = RepairOperations.builder()
                .id(1)
                .repairAction("To change the brakes")
                .price(5000)
                .build();

        car.setRepairOperationsList(Collections.singletonList(repairOperation));

        Part part = Part.builder()
                .id(1)
                .name("Brake")
                .price(500)
                .stock(10)
                .build();
        car.setPartsList(Collections.singletonList(part));
        Receipt receipt = Receipt.builder()
                .id(1)
                .carLicensePlate("LEJ 2091")
                .inspectionAmount(100)
                .status("pending")
                .partsList(car.getPartsList())
                .repairOperationsList(car.getRepairOperationsList())
                .repairOperationsAmount(200)
                .partsAmount(300)
                .totalAmountOfRepairing(726)
                .build();

        List<Receipt> receiptsList = new ArrayList<>(Collections.singletonList(receipt));

        when(receiptRepository.findAllByCarLicensePlate("LEJ 2091")).thenReturn(receiptsList);
        assertEquals(receiptsList, receiptService.getReceiptsByLicensePlate("LEJ 2091").getResult());
    }

    @Test
    void updateReceiptTest() {
        Customer customer = Customer.builder()
                .id(1L)
                .phoneNumber("+923125153352")
                .address("Gul berg Greens, Islamabad")
                .surname("Kamran Abbasi")
                .build();

        Car car = Car.builder()
                .id(1)
                .customer(customer)
                .licensePlate("LEJ 2091")
                .repairStatus("pending")
                .build();

        RepairOperations repairOperation = RepairOperations.builder()
                .id(1)
                .repairAction("To change the brakes")
                .price(5000)
                .build();

        car.setRepairOperationsList(Collections.singletonList(repairOperation));

        Part part = Part.builder()
                .id(1)
                .name("Brake")
                .price(500)
                .stock(10)
                .build();
        car.setPartsList(Collections.singletonList(part));
        Receipt receipt = Receipt.builder()
                .id(1)
                .carLicensePlate("LEJ 2091")
                .inspectionAmount(100)
                .status("pending")
                .partsList(car.getPartsList())
                .repairOperationsList(car.getRepairOperationsList())
                .repairOperationsAmount(200)
                .partsAmount(300)
                .totalAmountOfRepairing(726)
                .build();


        Receipt updatedReceipt = Receipt.builder()
                .id(1)
                .carLicensePlate("LEJ 2091")
                .inspectionAmount(100)
                .status("Paid")
                .partsList(car.getPartsList())
                .repairOperationsList(car.getRepairOperationsList())
                .repairOperationsAmount(200)
                .partsAmount(300)
                .totalAmountOfRepairing(726)
                .build();

        when(receiptRepository.findById(receipt.getId())).thenReturn(Optional.of(receipt));
        when(receiptRepository.saveAndFlush(updatedReceipt)).thenReturn(updatedReceipt);
        assertEquals(updatedReceipt, receiptService.updateReceipt(updatedReceipt).getResult());
    }

    @Test
    void deleteReceiptTest() {
        Customer customer = Customer.builder()
                .id(1L)
                .phoneNumber("+923125153352")
                .address("Gul berg Greens, Islamabad")
                .surname("Kamran Abbasi")
                .build();

        Car car = Car.builder()
                .id(1)
                .customer(customer)
                .licensePlate("LEJ 2091")
                .repairStatus("pending")
                .build();

        RepairOperations repairOperation = RepairOperations.builder()
                .id(1)
                .repairAction("To change the brakes")
                .price(5000)
                .build();

        car.setRepairOperationsList(Collections.singletonList(repairOperation));

        Part part = Part.builder()
                .id(1)
                .name("Brake")
                .price(500)
                .stock(10)
                .build();
        car.setPartsList(Collections.singletonList(part));
        Receipt receipt = Receipt.builder()
                .id(1)
                .carLicensePlate("LEJ 2091")
                .inspectionAmount(100)
                .status("pending")
                .partsList(car.getPartsList())
                .repairOperationsList(car.getRepairOperationsList())
                .repairOperationsAmount(200)
                .partsAmount(300)
                .totalAmountOfRepairing(726)
                .build();

        when(receiptRepository.findById(receipt.getId())).thenReturn(Optional.of(receipt));

        when(receiptRepository.saveAndFlush(receipt)).thenReturn(receipt);
        assertEquals(200, receiptService.deleteReceipt(1).getStatusCode());
    }

    @Test
    void changeStatusToPaidTest() {
        Customer customer = Customer.builder()
                .id(1L)
                .phoneNumber("+923125153352")
                .address("Gul berg Greens, Islamabad")
                .surname("Kamran Abbasi")
                .build();

        Car car = Car.builder()
                .id(1)
                .customer(customer)
                .licensePlate("LEJ 2091")
                .repairStatus("pending")
                .build();

        RepairOperations repairOperation = RepairOperations.builder()
                .id(1)
                .repairAction("To change the brakes")
                .price(5000)
                .build();

        car.setRepairOperationsList(Collections.singletonList(repairOperation));

        Part part = Part.builder()
                .id(1)
                .name("Brake")
                .price(500)
                .stock(10)
                .build();
        car.setPartsList(Collections.singletonList(part));
        Receipt receipt = Receipt.builder()
                .id(1)
                .carLicensePlate("LEJ 2091")
                .inspectionAmount(100)
                .status("pending")
                .partsList(car.getPartsList())
                .repairOperationsList(car.getRepairOperationsList())
                .repairOperationsAmount(200)
                .partsAmount(300)
                .totalAmountOfRepairing(726)
                .build();

        when(receiptRepository.findById(receipt.getId())).thenReturn(Optional.of(receipt));
        when(receiptRepository.save(receipt)).thenReturn(receipt);
        assertEquals(200, receiptService.deleteReceipt(1).getStatusCode());
    }
}
