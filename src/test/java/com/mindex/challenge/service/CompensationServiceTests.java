package com.mindex.challenge.service;

import com.mindex.challenge.dto.mapper.CompensationMapper;
import com.mindex.challenge.dto.model.CompensationDto;
import com.mindex.challenge.model.Compensation;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import static com.mindex.challenge.Utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceTests {

    @Autowired
    private CompensationService compensationService;

    @Test
    @DisplayName("Should retrieve existing compensation by employee id")
    public void testGetCompensationByEmployeeId() {

        // Create compensation instance
        Compensation expectedCompensation = getExistingCompensation();

        // Test the get compensation service method
        CompensationDto actualCompensation = compensationService.getCompensationByEmployeeId(EXISTING_COMPENSATION_EMPLOYEE_ID);

        // Assertions
        assertNotNull(actualCompensation);
        assertEquals(EXISTING_COMPENSATION_EMPLOYEE_ID, actualCompensation.getEmployeeId());
        assertCompensationEquivalence(expectedCompensation, CompensationMapper.mapToEntity(actualCompensation));

    }

    @Test
    @DisplayName("Should get Not Found exception when retrieve existing compensation by employee id")
    public void testGetCompensationByIdNotFound() {

        boolean exceptionThrown = false;

        try {
            compensationService.getCompensationByEmployeeId(NEW_EMPLOYEE_ID);
        } catch (RuntimeException e) {
            assertEquals("compensation.not.found", e.getMessage());
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown, "Expected Not Found exception was not thrown.");

    }

    @Test
    @DisplayName("Should update existing compensation")
    @Rollback
    public void testUpdateCompensation() {

        // Create compensation instance
        Compensation existingCompensation = getExistingCompensation();
        existingCompensation.setEmployeeId(EXISTING_COMPENSATION_EMPLOYEE_ID);

        // Modify compensation details
        existingCompensation.setSalary(new BigDecimal("195000.00"));
        existingCompensation.setEffectiveDate(convertJsonStringDateToUTCDate("2021-11-10T09:20:00.595Z"));

        // Test the update compensation service method
        CompensationDto actualCompensation = compensationService.updateCompensation(CompensationMapper.mapToDto(existingCompensation));

        // Assertions
        assertNotNull(actualCompensation);
        assertNotNull(actualCompensation.getEmployeeId());
        assertCompensationEquivalence(existingCompensation, CompensationMapper.mapToEntity(actualCompensation));

    }

    @Test
    @DisplayName("Should create new compensation")
    @Rollback
    public void testCreateCompensation() {

        // Create compensation instance
        Compensation newCompensation = getNewCompensation();

        // Test the create compensation service method
        CompensationDto result = compensationService.createCompensation(CompensationMapper.mapToDto(newCompensation));

        // Assertions
        assertNotNull(result);
        assertNotNull(result.getEmployeeId());
        assertCompensationEquivalence(newCompensation, CompensationMapper.mapToEntity(result));

    }

    @Test
    @DisplayName("Should get Duplicate exception when creating compensation that already exits")
    public void testCreateCompensationDuplicate() {

        Compensation compensation = getExistingCompensation();

        boolean exceptionThrown = false;

        try {
            compensationService.createCompensation(CompensationMapper.mapToDto(compensation));
        } catch (RuntimeException e) {
            assertEquals("compensation.duplicate", e.getMessage());
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown, "Expected Duplicate exception was not thrown.");

    }

    @Test
    @DisplayName("Should get Employee Not Found exception when creating compensation for employee that does not exist")
    public void testCreateCompensationForNonexistentEmployee() {

        Compensation compensation = getNewCompensation();
        compensation.setEmployeeId(NEW_EMPLOYEE_ID);

        boolean exceptionThrown = false;

        try {
            compensationService.createCompensation(CompensationMapper.mapToDto(compensation));
        } catch (RuntimeException e) {
            assertEquals("employee.not.found", e.getMessage());
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown, "Expected Duplicate exception was not thrown.");

    }

}
