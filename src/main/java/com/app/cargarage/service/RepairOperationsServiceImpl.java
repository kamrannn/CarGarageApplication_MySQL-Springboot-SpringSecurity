package com.app.cargarage.service;

import com.app.cargarage.dto.ResponseDto;
import com.app.cargarage.model.RepairOperations;
import com.app.cargarage.repository.RepairOperationsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
}
