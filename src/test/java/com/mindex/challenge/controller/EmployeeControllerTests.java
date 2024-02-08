package com.mindex.challenge.controller;

import com.mindex.challenge.model.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import static com.mindex.challenge.Utils.TestUtils.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeControllerTests {

    private String employeeUrl;
    private String employeeIdUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeService employeeService;

    @Before
    public void setup() {
        employeeUrl = LOCAL_HOST + port + CREATE_EMPLOYEE_URL;
        employeeIdUrl = LOCAL_HOST + port + UPDATE_EMPLOYEE_URL;
    }

    @Test
    @DisplayName("Should retrieve a existing employee given an employee id")
    public void testGetEmployeeEndpoint() {

        Employee readEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, EXISTING_EMPLOYEE_ID).getBody();

        assertNotNull(readEmployee);
        assertNotNull(readEmployee.getEmployeeId());
        assertEmployeeEquivalence(getExistingEmployee(), readEmployee);

    }

    @Test
    @DisplayName("Should update an existing employee")
    @Rollback
    public void testUpdateEmployeeEndpoint() {

        Employee existingEmployee = getExistingEmployee();
        existingEmployee.setEmployeeId(EXISTING_EMPLOYEE_ID);
        existingEmployee.setDepartment("Sales");
        existingEmployee.setPosition("Manager");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee updatedEmployee =
                restTemplate.exchange(employeeIdUrl,
                        HttpMethod.PUT,
                        new HttpEntity<>(existingEmployee, headers),
                        Employee.class,
                        existingEmployee.getEmployeeId()).getBody();

        assertNotNull(updatedEmployee);
        assertNotNull(updatedEmployee.getEmployeeId());
        assertEmployeeEquivalence(existingEmployee, updatedEmployee);

    }

    @Test
    @DisplayName("Should create a new employee")
    @Rollback
    public void testCreateEmployeeEndpoint() {

        Employee newEmployee = getNewEmployee();
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, newEmployee, Employee.class).getBody();

        assertNotNull(createdEmployee);
        assertNotNull(createdEmployee.getEmployeeId());
        assertEmployeeEquivalence(newEmployee, createdEmployee);

    }
}
