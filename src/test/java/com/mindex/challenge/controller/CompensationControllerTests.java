package com.mindex.challenge.controller;

import com.mindex.challenge.model.Compensation;
import com.mindex.challenge.service.CompensationService;
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
import java.math.BigDecimal;
import static com.mindex.challenge.Utils.TestUtils.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompensationControllerTests {

    private String compensationUrl;
    private String compensationIdUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CompensationService compensationService;

    @Before
    public void setup() {
        compensationUrl = LOCAL_HOST + port + CREATE_COMPENSATION_URL;
        compensationIdUrl = LOCAL_HOST + port + UPDATE_COMPENSATION_URL;
    }

    @Test
    @DisplayName("Should retrieve a existing compensation given an compensation id")
    public void testGetCompensationEndpoint() {

        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, EXISTING_COMPENSATION_EMPLOYEE_ID).getBody();

        assertNotNull(readCompensation);
        assertNotNull(readCompensation.getEmployeeId());
        assertCompensationEquivalence(getExistingCompensation(), readCompensation);

    }

    @Test
    @DisplayName("Should update an existing compensation")
    @Rollback
    public void testUpdateCompensationEndpoint() {

        Compensation existingCompensation = getExistingCompensation();
        existingCompensation.setEmployeeId(EXISTING_COMPENSATION_EMPLOYEE_ID);
        existingCompensation.setSalary(new BigDecimal("125000.00"));
        existingCompensation.setEffectiveDate(convertJsonStringDateToUTCDate("2018-03-20T11:15:00Z"));


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Compensation updatedCompensation =
                restTemplate.exchange(compensationIdUrl,
                        HttpMethod.PUT,
                        new HttpEntity<>(existingCompensation, headers),
                        Compensation.class,
                        existingCompensation.getEmployeeId()).getBody();

        assertNotNull(updatedCompensation);
        assertNotNull(updatedCompensation.getEmployeeId());
        assertCompensationEquivalence(existingCompensation, updatedCompensation);

    }

    @Test
    @DisplayName("Should create a new compensation")
    @Rollback
    public void testCreateCompensationEndpoint() {

        Compensation newCompensation = getNewCompensation();
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, newCompensation, Compensation.class).getBody();

        assertNotNull(createdCompensation);
        assertNotNull(createdCompensation.getEmployeeId());
        assertCompensationEquivalence(newCompensation, createdCompensation);

    }

}