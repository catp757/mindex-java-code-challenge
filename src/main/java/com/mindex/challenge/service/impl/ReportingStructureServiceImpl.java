package com.mindex.challenge.service.impl;

import com.mindex.challenge.dto.mapper.EmployeeMapper;
import com.mindex.challenge.dto.model.EmployeeDto;
import com.mindex.challenge.exception.EntityType;
import com.mindex.challenge.model.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.mindex.challenge.exception.CustomExceptions.customException;
import static com.mindex.challenge.exception.ExceptionType.ENTITY_NOT_FOUND;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    /**
     * Retrieves the reporting structure for a given employee id
     *
     * @param employeeId
     * @return
     */
    @Override
    public ReportingStructure getReportingStructureForEmployeeId(String employeeId) {
        LOG.debug("Retrieving the reporting structure for employee id {}", employeeId);

        Optional<EmployeeDto> employeeDto = Optional.ofNullable(employeeService.getEmployeeById(employeeId));
        if (employeeDto.isPresent()) {

            // get all the reports for the given employee
            int totalReports = getAllReports(employeeId).size();

            // fill the reporting structure
            ReportingStructure reportingStructure = new ReportingStructure();
            reportingStructure.setEmployee(EmployeeMapper.mapToEntity(employeeDto.get()));
            reportingStructure.setNumberOfReports(totalReports);

            return reportingStructure;

        }
        throw customException(EntityType.EMPLOYEE, ENTITY_NOT_FOUND, employeeId);

    }

    /**
     * Recursive method that retrieves a list of employee ids that report to a given employee
     *
     * @param employeeId
     * @return
     */
    public List<String> getAllReports(String employeeId) {
        List<String> allReports = new ArrayList<>();
        Optional<EmployeeDto> employeeDto = Optional.ofNullable(employeeService.getEmployeeById(employeeId));
        if (employeeDto.isPresent()) {
            List<String> directReports = employeeDto.get().getDirectReports();
            boolean hasDirectReports = (directReports != null) && (!directReports.isEmpty());
            if (hasDirectReports) {
                for (String directReport: directReports) {
                    allReports.add(directReport);
                    allReports.addAll(getAllReports(directReport));
                }
            }
        }
        return allReports;
    }

}
