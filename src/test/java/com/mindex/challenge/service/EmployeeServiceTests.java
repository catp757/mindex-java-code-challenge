package com.mindex.challenge.service;

import com.mindex.challenge.Utils.TestUtils;
import com.mindex.challenge.dto.mapper.EmployeeMapper;
import com.mindex.challenge.dto.model.EmployeeDto;
import com.mindex.challenge.model.Employee;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import static com.mindex.challenge.Utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceTests {

    @Autowired
    private EmployeeService employeeService;

    @Test
    @DisplayName("Should retrieve existing employee by id")
    public void testGetEmployeeById() {

        // Create employee instance
        Employee expectedEmployee = getExistingEmployee();

        // Test the get employee service method
        EmployeeDto actualEmployee = employeeService.getEmployeeById(EXISTING_EMPLOYEE_ID);

        // Assertions
        assertNotNull(actualEmployee);
        assertEquals(EXISTING_EMPLOYEE_ID, actualEmployee.getEmployeeId());
        assertEmployeeEquivalence(expectedEmployee, EmployeeMapper.mapToEntity(actualEmployee));

    }

    @Test
    @DisplayName("Should get Not Found exception when retrieve existing employee by id")
    public void testGetEmployeeByIdNotFound() {

        boolean exceptionThrown = false;

        try {
            employeeService.getEmployeeById(NEW_EMPLOYEE_ID);
        } catch (RuntimeException e) {
            assertEquals("employee.not.found", e.getMessage());
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown, "Expected Not Found exception was not thrown.");

    }

    @Test
    @DisplayName("Should create new employee")
    @Rollback
    public void testCreateEmployee() {

        // Create employee instance
        Employee newEmployee = TestUtils.getNewEmployee();

        // Test the create service method
        EmployeeDto result = employeeService.createEmployee(EmployeeMapper.mapToDto(newEmployee));

        // Assertions
        assertNotNull(result);
        assertNotNull(result.getEmployeeId());
        assertEmployeeEquivalence(newEmployee, EmployeeMapper.mapToEntity(result));

    }

    @Test
    @DisplayName("Should update existing employee")
    @Rollback
    public void testUpdateEmployee() {

        // Create employee instance
        Employee existingEmployee = TestUtils.getExistingEmployee();
        existingEmployee.setEmployeeId(EXISTING_EMPLOYEE_ID);

        // Modify employee details
        existingEmployee.setLastName("Connelly");
        existingEmployee.setPosition("Manager");
        existingEmployee.setDepartment("Sales");

        // Test the service method
        EmployeeDto actualEmployee = employeeService.updateEmployee(EmployeeMapper.mapToDto(existingEmployee));

        // Assertions
        assertNotNull(actualEmployee);
        assertNotNull(actualEmployee.getEmployeeId());
        assertEmployeeEquivalence(existingEmployee, EmployeeMapper.mapToEntity(actualEmployee));

    }

}
