package com.mindex.challenge;

import com.mindex.challenge.model.Compensation;
import com.mindex.challenge.repository.CompensationRepository;
import com.mindex.challenge.repository.EmployeeRepository;
import com.mindex.challenge.model.Employee;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.mindex.challenge.Utils.TestUtils.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataBootstrapTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    @Test
    @DisplayName("Data should be bootstrapped successfully")
    public void testDataBootstrap() {

        ValidateEmployeeData();
        ValidateCompensationData();

    }

    // Method validates that the employee data exists
    public void ValidateEmployeeData() {

        // validate that employee data exists
        Employee employee = employeeRepository.findByEmployeeId(EXISTING_EMPLOYEE_ID);
        assertNotNull(employee);
        assertEmployeeEquivalence(getExistingEmployee(), employee);

        // validate the number of employee records
        int employeeCount = employeeRepository.findAll().size();
        assertEquals(8, employeeCount);

    }

    // Method validates that the compensation data exists
    public void ValidateCompensationData() {

        // validate that compensation data exists
        Compensation compensation = compensationRepository.findByEmployeeId(EXISTING_COMPENSATION_EMPLOYEE_ID);
        assertNotNull(compensation);
        assertCompensationEquivalence(getExistingCompensation(), compensation);

        // validate the number of compensation records
        int compensationCount = compensationRepository.findAll().size();
        assertEquals(5, compensationCount);

    }
}