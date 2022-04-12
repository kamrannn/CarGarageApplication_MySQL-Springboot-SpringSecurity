package com.app.cargarage.service;

import com.app.cargarage.dto.ResponseDto;
import com.app.cargarage.model.Part;

public interface PartService {
    ResponseDto save(Part part);

    ResponseDto list();
}
