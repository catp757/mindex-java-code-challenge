package com.mindex.challenge.controller;

import com.mindex.challenge.model.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ReportingStructureController {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;

    @GetMapping("/employee/reportingStructure/{id}")
    public ResponseEntity<?> getReportingStructureForEmployeeId(@PathVariable String id) {
        LOG.debug("Received reporting structure request for employee id [{}]", id);

        ReportingStructure reportingStructure = reportingStructureService.getReportingStructureForEmployeeId(id);
        if (reportingStructure != null) {
            return ResponseEntity.ok(reportingStructure);
        }

        LOG.debug("Error retrieving reporting structure request for employee. Employee with id {} not found.", id);
        return ResponseEntity.badRequest().body("Could not retrieve the reporting structure. Employee with id " + id + " not found.");
    }
}
