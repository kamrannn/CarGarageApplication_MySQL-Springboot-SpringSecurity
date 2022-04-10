package com.app.cargarage.service;

import com.app.cargarage.dto.ResponseDto;
import com.app.cargarage.model.Car;
import com.app.cargarage.model.Customer;
import org.springframework.stereotype.Service;

public interface CustomerService {
    public ResponseDto addCustomer(Customer customer);
    public ResponseDto listOfCustomers();
}
