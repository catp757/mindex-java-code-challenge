package com.mindex.challenge.service.impl;

import com.mindex.challenge.dto.mapper.EmployeeMapper;
import com.mindex.challenge.dto.model.EmployeeDto;
import com.mindex.challenge.exception.EntityType;
import com.mindex.challenge.repository.EmployeeRepository;
import com.mindex.challenge.model.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;
import static com.mindex.challenge.exception.CustomExceptions.customException;
import static com.mindex.challenge.exception.ExceptionType.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Creates a new employee
     *
     * @param employeeDto
     * @return
     */
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        LOG.debug("Creating employee [{}]", employeeDto);

        // verify that the employee does not already exist
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByEmployeeId(employeeDto.getEmployeeId()));
        if (!employee.isPresent()) {
            Employee employeeModel = EmployeeMapper.mapToEntity(employeeDto);
            employeeModel.setEmployeeId(UUID.randomUUID().toString());
            Employee savedEmployee = employeeRepository.save(employeeModel);
            return EmployeeMapper.mapToDto(savedEmployee);
        }

        LOG.debug("Could not create employee. Employee [{}] already exists.", employeeDto);
        throw customException(EntityType.EMPLOYEE, DUPLICATE_ENTITY, employeeDto.getEmployeeId(), employeeDto.getFullName());
    }

    /**
     * Retrieves an employee using the employee id
     *
     * @param id
     * @return
     */
    @Override
    public EmployeeDto getEmployeeById(String id) {
        LOG.debug("Retrieving employee {}", id);

        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByEmployeeId(id));
        if (employee.isPresent()) {
            return EmployeeMapper.mapToDto(employee.get());
        }

        LOG.debug("Could not retrieve employee. Employee id {} not found.", id);
        throw customException(EntityType.EMPLOYEE, ENTITY_NOT_FOUND, id);
    }

    /**
     * Updates an existing employee
     *
     * @param employeeDto
     * @return
     */
    @Override
    @Transactional
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        LOG.debug("Updating employee [{}]", employeeDto);

        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByEmployeeId(employeeDto.getEmployeeId()));
        if (employee.isPresent()) {
            Employee savedEmployee = employeeRepository.save(EmployeeMapper.mapToEntity(employeeDto));
            return EmployeeMapper.mapToDto(savedEmployee);
        }

        LOG.debug("Could not update employee. Employee id {} not found.", employeeDto.getEmployeeId());
        throw customException(EntityType.EMPLOYEE, ENTITY_NOT_FOUND, employeeDto.getEmployeeId());
    }

}
