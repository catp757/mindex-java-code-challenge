package com.mindex.challenge.controller;

import com.mindex.challenge.model.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import static com.mindex.challenge.Utils.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureControllerTest {

    private String ReportingStructureIdUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReportingStructure reportingStructure;

    @Before
    public void setup() {
        ReportingStructureIdUrl = LOCAL_HOST + port + REPORTING_STRUCTURE_URL;
    }

    @Test
    @DisplayName("Should return a reporting structure for an existing employee")
    public void testReportingStructureRead() {

        ReportingStructure readReportingStructure = restTemplate.getForEntity(ReportingStructureIdUrl,
                                                    ReportingStructure.class, EXISTING_EMPLOYEE_ID).getBody();
        assertNotNull(readReportingStructure);
        assertEquals(EXISTING_EMPLOYEE_ID, readReportingStructure.getEmployee().getEmployeeId());
        assertEquals(7, readReportingStructure.getNumberOfReports());

    }
}