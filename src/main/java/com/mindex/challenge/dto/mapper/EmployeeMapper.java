package com.mindex.challenge.dto.mapper;


import com.mindex.challenge.dto.model.EmployeeDto;
import com.mindex.challenge.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public static EmployeeDto mapToDto(Employee employee) {
        return new EmployeeDto()
                .setEmployeeId(employee.getEmployeeId())
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setPosition(employee.getPosition())
                .setDepartment(employee.getDepartment())
                .setDirectReports(employee.getDirectReports());
    }

    public static Employee mapToEntity(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            return null;
        }

        Employee employee = new Employee();
        if (employeeDto.getEmployeeId() != null) {
            employee.setEmployeeId(employeeDto.getEmployeeId());
        }
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPosition(employeeDto.getPosition());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setDirectReports(employeeDto.getDirectReports());

        return employee;
    }
}