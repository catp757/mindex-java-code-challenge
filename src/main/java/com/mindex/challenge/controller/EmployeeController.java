package com.mindex.challenge.controller;

import com.mindex.challenge.config.SwaggerConfig;
import com.mindex.challenge.dto.model.EmployeeDto;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {SwaggerConfig.EMPLOYEE_BOOK_TAG})
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    // Create a new employee
    @PostMapping("/employee")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        LOG.debug("Received create request for employee [{}]", employeeDto);
        try {

            EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);

        } catch (Exception ex) {
            LOG.debug("Error creating employee [{}]", employeeDto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating employee. " + ex.getMessage());
        }
    }

    // Get employee by ID
    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String id) {
        LOG.debug("Received read request for employee {}", id);
        try {

            EmployeeDto employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);

        } catch (Exception ex) {
            LOG.debug("Error retrieving employee {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee with id " + id + " not found. " + ex.getMessage());
        }

    }

    // Update an existing employee
    @PutMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable String id,
            @Valid @RequestBody EmployeeDto employeeDto
    ) {
        LOG.debug("Received update request for employee [{}]", employeeDto);
        try {

            EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeDto);
            return ResponseEntity.ok(updatedEmployee);

        } catch (Exception ex) {
            LOG.debug("Error updating employee [{}]", employeeDto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating employee id " + id + ". " + ex.getMessage());
        }

    }

}
