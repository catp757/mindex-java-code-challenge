package com.mindex.challenge.service.impl;

import com.mindex.challenge.dto.mapper.CompensationMapper;
import com.mindex.challenge.dto.model.CompensationDto;
import com.mindex.challenge.exception.*;
import com.mindex.challenge.model.Employee;
import com.mindex.challenge.repository.CompensationRepository;
import com.mindex.challenge.repository.EmployeeRepository;
import com.mindex.challenge.model.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static com.mindex.challenge.exception.CustomExceptions.customException;
import static com.mindex.challenge.exception.ExceptionType.DUPLICATE_ENTITY;
import static com.mindex.challenge.exception.ExceptionType.ENTITY_NOT_FOUND;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    
    /**
     * Creates a compensation
     *
     * @param compensationDto
     * @return
     */
    @Override
    public CompensationDto createCompensation(CompensationDto compensationDto) {

        LOG.debug("Creating compensation [{}]", compensationDto);

        // verify that this employee exists in the employee database
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByEmployeeId(compensationDto.getEmployeeId()));
        if (!employee.isPresent()) {
            LOG.debug("Could not create compensation for employee. Employee id {} not found.", compensationDto.getEmployeeId());
            throw customException(EntityType.EMPLOYEE, ENTITY_NOT_FOUND, compensationDto.getEmployeeId());
        }

        // verify that the compensation record for this employee does not already exist
        Optional<Compensation> compensation = Optional.ofNullable(compensationRepository.findByEmployeeId(compensationDto.getEmployeeId()));
        if (!compensation.isPresent()) {
            Compensation compensationModel = CompensationMapper.mapToEntity(compensationDto);
            Compensation savedCompensation = compensationRepository.save(compensationModel);
            return CompensationMapper.mapToDto(savedCompensation);
        }

        LOG.debug("Could not create compensation. Compensation for employee {} already exists.", compensationDto.getEmployeeId());
        throw customException(EntityType.COMPENSATION, DUPLICATE_ENTITY, compensationDto.getEmployeeId());

    }

    /**
     * Retrieves a compensation for a given employee id
     *
     * @param id
     * @return
     */
    @Override
    public CompensationDto getCompensationByEmployeeId(String id) {

        LOG.debug("Retrieving compensation for employee id {}", id);

        Optional<Compensation> compensation = Optional.ofNullable(compensationRepository.findByEmployeeId(id));
        if (compensation.isPresent()) {
            return CompensationMapper.mapToDto(compensation.get());
        }

        LOG.debug("Could not retrieve compensation. Employee id {} not found.", id);
        throw customException(EntityType.COMPENSATION, ENTITY_NOT_FOUND, id);

    }

    /**
     * Updates a compensation
     *
     * @param compensationDto
     * @return
     */
    @Override
    @Transactional
    public CompensationDto updateCompensation(CompensationDto compensationDto) {

        LOG.debug("Updating compensation [{}]", compensationDto);

        // verify that this employee exists in the employee database
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByEmployeeId(compensationDto.getEmployeeId()));
        if (!employee.isPresent()) {
            LOG.debug("Could not update compensation for employee. Employee id {} not found.", compensationDto.getEmployeeId());
            throw customException(EntityType.EMPLOYEE, ENTITY_NOT_FOUND, compensationDto.getEmployeeId());
        }

        Optional<Compensation> compensation = Optional.ofNullable(compensationRepository.findByEmployeeId(compensationDto.getEmployeeId()));
        if (compensation.isPresent()) {
            Compensation savedCompensation = compensationRepository.save(CompensationMapper.mapToEntity(compensationDto));
            return CompensationMapper.mapToDto(savedCompensation);
        }

        LOG.debug("Could not update compensation. Compensation for employee id {} not found.", compensationDto.getEmployeeId());
        throw customException(EntityType.COMPENSATION, ENTITY_NOT_FOUND, compensationDto.getEmployeeId());

    }

}
