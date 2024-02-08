package com.mindex.challenge;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.repository.CompensationRepository;
import com.mindex.challenge.repository.EmployeeRepository;
import com.mindex.challenge.model.Employee;
import com.mindex.challenge.model.Compensation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class DataBootstrap {
    private static final String EMPLOYEE_DATASTORE_LOCATION = "/static/employee_database.json";

    private static final String COMPENSATION_DATASTORE_LOCATION = "/static/compensation_database.json";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() throws IOException {
        loadEmployeeData();
        loadCompensationData();
    }

    public void loadEmployeeData() throws IOException {
        TypeReference<List<Employee>> typeReference = new TypeReference<>(){};
        InputStream inputStream = typeReference.getClass().getResourceAsStream(EMPLOYEE_DATASTORE_LOCATION);

        List<Employee> employees = objectMapper.readValue(inputStream, typeReference);

        for (Employee employee : employees) {
            employeeRepository.save(employee);
        }
    }

    public void loadCompensationData() throws IOException {
        TypeReference<List<Compensation>> typeReference = new TypeReference<>(){};
        InputStream inputStream = typeReference.getClass().getResourceAsStream(COMPENSATION_DATASTORE_LOCATION);

        List<Compensation> compensations = objectMapper.readValue(inputStream, typeReference);

        for (Compensation compensation : compensations) {
            compensationRepository.save(compensation);
        }
    }
}
