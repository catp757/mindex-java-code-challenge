package com.mindex.challenge.service;

import com.mindex.challenge.dto.model.CompensationDto;

public interface CompensationService {
    CompensationDto createCompensation(CompensationDto compensationDto);
    CompensationDto getCompensationByEmployeeId(String id);
    CompensationDto updateCompensation(CompensationDto compensationDto);
}
