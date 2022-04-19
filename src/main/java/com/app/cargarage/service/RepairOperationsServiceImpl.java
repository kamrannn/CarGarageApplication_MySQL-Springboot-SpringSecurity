package com.app.cargarage.service;

import com.app.cargarage.dto.ResponseDto;
import com.app.cargarage.model.Receipt;
import com.app.cargarage.model.RepairOperations;
import com.app.cargarage.repository.RepairOperationsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairOperationsServiceImpl implements RepairOperationsService {

    private final RepairOperationsRepository repairOperationsRepository;

    public RepairOperationsServiceImpl(RepairOperationsRepository repairOperationsRepository) {
        this.repairOperationsRepository = repairOperationsRepository;
    }

    @Override
    public ResponseDto addRepairOperations(RepairOperations repairOperations) {
        try {
            return ResponseDto.builder()
                    .result(repairOperationsRepository.saveAndFlush(repairOperations))
                    .message("Repair operations is successfully added in the database")
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
    public ResponseDto listOfRepairOperations() {
        try {
            List<RepairOperations> repairOperationsList = repairOperationsRepository.findAll();
            if (repairOperationsList.isEmpty()) {
                return ResponseDto.builder()
                        .result(repairOperationsList)
                        .statusCode(HttpStatus.OK.value())
                        .message("There is no repair operation generated yet in the database")
                        .build();
            } else {
                return ResponseDto.builder()
                        .result(repairOperationsList)
                        .statusCode(HttpStatus.OK.value())
                        .message("This is the list of generated repair operation in the database")
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
