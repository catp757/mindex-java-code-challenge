package com.mindex.challenge.Utils;

import com.mindex.challenge.model.Compensation;
import com.mindex.challenge.model.Employee;
import org.junit.Assert;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TestUtils {

    public static final String LOCAL_HOST = "http://localhost:";
    public static final String CREATE_EMPLOYEE_URL = "api/v1/employee";
    public static final String UPDATE_EMPLOYEE_URL = "api/v1/employee/{id}";

    public static final String NEW_EMPLOYEE_ID = "19ff8aa5-9efd-4ae9-8741-98a885a224a4";
    public static final String EXISTING_EMPLOYEE_ID = "67b36e94-3e60-4d20-a2c8-4f13e8f94722";

    public static final String CREATE_COMPENSATION_URL = "api/v1/compensation";
    public static final String UPDATE_COMPENSATION_URL = "api/v1/compensation/{id}";

    public static final String NEW_COMPENSATION_EMPLOYEE_ID = "f91d1879-2949-499f-a9fd-0d7db4bc6468";
    public static final String EXISTING_COMPENSATION_EMPLOYEE_ID = "16a596ae-edd3-4847-99fe-c4518e82c86f";

    public static final String REPORTING_STRUCTURE_URL = "/api/v1/employee/reportingStructure/{id}";

    // Employee test helper methods

    // Method returns an instance of an existing employee
    public static Employee getExistingEmployee() {
        Employee existingEmployee = new Employee();
        existingEmployee.setFirstName("Mike");
        existingEmployee.setLastName("Jones");
        existingEmployee.setPosition("CEO");
        existingEmployee.setDepartment("Management");
        return existingEmployee;
    }

    // Method returns an instance of a new employee
    public static Employee getNewEmployee() {
        Employee newEmployee = new Employee();
        newEmployee.setFirstName("Frank");
        newEmployee.setLastName("Donnelly");
        newEmployee.setPosition("Manager");
        newEmployee.setDepartment("Product");
        return newEmployee;
    }

    // Method verifies that the actual and expected employee details match
    public static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getLastName(), actual.getLastName());
        Assert.assertEquals(expected.getDepartment(), actual.getDepartment());
        Assert.assertEquals(expected.getPosition(), actual.getPosition());
    }

    // Compensation test helper methods

    // Method returns an instance of an existing compensation
    public static Compensation getExistingCompensation() {

        String dateString = "2019-10-15T09:00:00Z";
        Date date = convertJsonStringDateToUTCDate(dateString);

        Compensation existingCompensation = new Compensation();
        existingCompensation.setEmployeeId(EXISTING_COMPENSATION_EMPLOYEE_ID);
        existingCompensation.setSalary(new BigDecimal("190000.00"));
        existingCompensation.setEffectiveDate(date);

        return existingCompensation;
    }

    // Method returns an instance of a new compensation
    public static Compensation getNewCompensation() {

        String dateString = "2020-09-12T10:00:00Z";
        Date date = convertJsonStringDateToUTCDate(dateString);

        Compensation newCompensation = new Compensation();
        newCompensation.setEmployeeId(NEW_COMPENSATION_EMPLOYEE_ID);
        newCompensation.setSalary(new BigDecimal("80000.00"));
        newCompensation.setEffectiveDate(date);

        return newCompensation;
    }

    // Method converts a json string date into a UTC date
    public static Date convertJsonStringDateToUTCDate(String jsonStringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return dateFormat.parse(jsonStringDate);
        } catch (ParseException e) {
            return null;
        }
    }

    // Method verifies actual and expected compensation details match
    public static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        Assert.assertEquals(expected.getSalary(), actual.getSalary());
        Assert.assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }

}
