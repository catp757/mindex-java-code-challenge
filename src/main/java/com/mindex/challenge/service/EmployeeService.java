package com.mindex.challenge.service;

import com.mindex.challenge.dto.model.EmployeeDto;

public interface EmployeeService {
    EmployeeDto getEmployeeById(String id);
    EmployeeDto updateEmployee(EmployeeDto employeeDto);
    EmployeeDto createEmployee(EmployeeDto employeeDto);
}
