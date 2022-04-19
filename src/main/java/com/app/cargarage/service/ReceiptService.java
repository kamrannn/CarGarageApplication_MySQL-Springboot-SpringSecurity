package com.app.cargarage.service;

import com.app.cargarage.dto.ResponseDto;

public interface ReceiptService {
    ResponseDto generateReceipt(String carLicensePlate);
    ResponseDto listReceipts();
    ResponseDto getReceiptsByLicensePlate(String licensePlate);
}
