package com.mindex.challenge.dto.mapper;

import com.mindex.challenge.dto.model.CompensationDto;
import com.mindex.challenge.model.Compensation;
import org.springframework.stereotype.Component;

@Component
public class CompensationMapper {

    public static CompensationDto mapToDto(Compensation compensation) {
        return new CompensationDto()
                .setEmployeeId(compensation.getEmployeeId())
                .setSalary(compensation.getSalary())
                .setEffectiveDate(compensation.getEffectiveDate());

    }

    public static Compensation mapToEntity(CompensationDto compensationDto) {
        if (compensationDto == null) {
            return null;
        }

        return new Compensation()
            .setEmployeeId(compensationDto.getEmployeeId())
            .setSalary(compensationDto.getSalary())
            .setEffectiveDate(compensationDto.getEffectiveDate());
    }

}
