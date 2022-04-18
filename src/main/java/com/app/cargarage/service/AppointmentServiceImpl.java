package com.app.cargarage.service;

import com.app.cargarage.dto.ResponseDto;
import com.app.cargarage.model.Appointment;
import com.app.cargarage.model.Car;
import com.app.cargarage.model.Customer;
import com.app.cargarage.repository.AppointmentRepository;
import com.app.cargarage.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final CustomerRepository customerRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, CustomerRepository customerRepository) {
        this.appointmentRepository = appointmentRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public ResponseDto create(Appointment appointment, long customerId) {
        try {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if (customer.isPresent()) {
                appointment.setCustomer(customer.get());
            } else {
                return ResponseDto.builder()
                        .result(null)
                        .message("There is no customer against this id")
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build();
            }
//            customerRepository.saveAndFlush(appointment.getCustomer());
            return ResponseDto.builder()
                    .result(appointmentRepository.saveAndFlush(appointment))
                    .message("Appointment is successfully created in the database against customer: " + appointment.getCustomer().getSurname())
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
    public ResponseDto listOfAppointments() {
        try {
            List<Appointment> appointments = appointmentRepository.findAll();
            if (appointments.isEmpty()) {
                return ResponseDto.builder()
                        .result(appointments)
                        .message("There is no appointment registered yet in the database")
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build();
            } else {
                return ResponseDto.builder()
                        .result(appointments)
                        .message("This is the list of appointments that are in the database")
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
